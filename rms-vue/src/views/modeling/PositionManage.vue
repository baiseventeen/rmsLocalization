<script setup>
import {getCurrentInstance, onMounted, reactive, inject, ref} from "vue";
import {Search,Refresh} from "@element-plus/icons-vue";
import {ElNotification} from "element-plus";

const {proxy} = getCurrentInstance()

const doRefresh = inject("reload")

const queryInfo = reactive({
  pageNow : 1,
  pageSize: 10,
  name: null,
  levelId: null,
  developmentTechId: null
})

const tableData = reactive({
  total: 0,
  positionData: []
})

const levelOptionMap = new ref({})

const handleOpenLevelSelect = ()=>{
  proxy.$http({
    url: "/modeling/positionManage/findLevel",
    method: "get",
  }).then((res)=>{
    levelOptionMap.value=res.data.data
  }).catch((ex)=>{
    proxy.$message.error(ex.message)
  })
}

const techOptionMap = new ref({})

const handleOpenTechSelect = ()=>{
  proxy.$http({
    url: "/modeling/developmentTechManage/findAll",
    method: "get",
  }).then((res)=>{
    techOptionMap.value=res.data.data
  }).catch((ex)=>{
    proxy.$message.error(ex.message)
  })
}


const handleSearch = ()=>{
  proxy.$http({
    url: "/modeling/positionManage/page",
    method: "post",
    data: queryInfo
  }).then((res)=>{
    tableData.total=res.data.data.totalRows
    tableData.positionData=res.data.data.rowData

  }).catch((ex)=>{
    proxy.$message.error(ex.message)
  })
}

const dialogConfig = reactive({
  type: "",
  visible: false,
  title: "",
  disable: false
})

const changeDialog = (type)=>{
  dialogConfig.visible=true;
  switch (type){
    case "add": {
      dialogConfig.type="add";
      dialogConfig.disable=false;
      dialogConfig.title="新增岗位";
      break;
    }
    case "edit": {
      dialogConfig.type="edit";
      dialogConfig.disable=false;
      dialogConfig.title="修改岗位信息";
      break;
    }
    case "show": {
      dialogConfig.type="show";
      dialogConfig.disable=true;
      dialogConfig.title="岗位详情";
      break;
    }
  }
}

const newPosition = reactive({
  id: null,
  positionName: null,
  positionLevelId: null,
  salaryMax: null,
  salaryMin: null,
  developmentTechId: null,
  productivity: null,
})

const clearNewPosition = ()=>{
  Object.assign(newPosition, {
    id: null,
    positionName: null,
    positionLevelId: null,
    salaryMax: null,
    salaryMin: null,
    developmentTechId: null,
    productivity: null,
  });
}

const handleSummit = ()=>{

  const handleConfirm = ()=>{
    proxy.$http({
      url: "/modeling/positionManage/"+(dialogConfig.type==="add"?"add":"update"),
      method: dialogConfig.type==="add"?"post":"put",
      data: newPosition,
    }).then((res)=>{
      if(res.data.code===200){
        ElNotification({
          title: dialogConfig.type==="add"?'添加成功':'修改成功',
          type: 'success',
        })
      }else{
        proxy.$message.error(res.data.msg)
      }
    }).catch((ex)=>{
      proxy.$message.error(ex.message)
    })

    dialogConfig.visible=false;

    doRefresh()
  }

  if(!(techOptionMap.value).hasOwnProperty(newPosition.developmentTechId)){
    proxy.$http({
      url:"/modeling/developmentTechManage/add",
      method: "post",
      data: {
        name: newPosition.developmentTechId
      }
    }).then((res)=>{
      if(res.data.code===200){
        newPosition.developmentTechId=res.data.data
        handleConfirm()

      }else{
        proxy.$message.error(res.data.msg)
      }
    }).catch((ex)=>{
      proxy.$message.error(ex.message)
    })
  }else {
    handleConfirm()
  }


}

const handleShowDetail = (row)=>{
  Object.assign(newPosition,row)
  changeDialog('show')
}

const handleEdit = (row)=>{
  Object.assign(newPosition,row)
  changeDialog('edit')
}

const handleDel = (row)=>{
  proxy.$http({
    url: "/modeling/positionManage/"+row.id,
    method: "delete",
  }).then((res)=>{
    if(res.data.code===200){
      ElNotification({
        title: '删除成功',
        type: 'success',
      })
    }else{
      proxy.$message.error(res.data.msg)
    }
  }).catch((ex)=>{
    proxy.$message.error(ex.message)
  })

  doRefresh()
}

const handleCurrentChange =(index)=>{
  queryInfo.pageNow=index;
  handleSearch();
}


onMounted(()=>{
  handleSearch()
  handleOpenLevelSelect()
  handleOpenTechSelect()
})
</script>

<template>

  <el-dialog v-model="dialogConfig.visible" style="width: 300px" :title="dialogConfig.title" @close="clearNewPosition">
    <el-form label-position="left" label-width="auto" :disabled="dialogConfig.disable">
      <el-form-item label="岗位名">
        <el-input v-model="newPosition.positionName" placeholder="请输入岗位名" clearable/>
      </el-form-item>
      <el-form-item label="岗位级别">
        <el-select
            v-model="newPosition.positionLevelId"
            @click="handleOpenLevelSelect"
            clearable
        >
          <el-option
              v-for="item in Object.values(levelOptionMap)"
              :label="item.position_level_name"
              :value="item.position_level_id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="薪资下限">
        <el-input v-model="newPosition.salaryMin" clearable/>
      </el-form-item>
      <el-form-item label="薪资上限">
        <el-input v-model="newPosition.salaryMax" clearable />
      </el-form-item>
      <el-form-item label="开发技术">
        <el-select
            v-model="newPosition.developmentTechId"
            allow-create
            filterable
            clearable
            default-first-option
            @click="handleOpenTechSelect"
        >
          <el-option v-for="item in Object.values(techOptionMap)"
                     :value="item.development_tech_id"
                     :label="item.development_tech_name"/>

        </el-select>
      </el-form-item>
      <el-form-item label="产能数值">
        <el-input v-model="newPosition.productivity" clearable/>
      </el-form-item>
    </el-form>
    <template v-if="!dialogConfig.disable">
      <el-button type="primary" @click="handleSummit">确认</el-button>
      <el-button @click="dialogConfig.visible=false">取消</el-button>
    </template>

  </el-dialog>

  <el-card style="height: 75px;" shadow="never">
    <el-form :inline="true" :model="queryInfo" >
      <el-form-item label="岗位名称" style="width: max-content">
        <el-input v-model="queryInfo.name" placeholder="请输入岗位名称" clearable style="width: 150px;"/>
      </el-form-item>
      <el-form-item label="岗位级别" style="width: max-content;">
        <el-select
            v-model="queryInfo.levelId"
            @click="handleOpenLevelSelect"
            clearable
            style="width: 150px;"
        >
          <el-option
              v-for="item in Object.values(levelOptionMap)"
              :label="item.position_level_name"
              :value="item.position_level_id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="开发技术" style="width: max-content">
        <el-select v-model="queryInfo.developmentTechId" clearable @click="handleOpenTechSelect" style="width: 150px">
          <el-option v-for="item in Object.values(techOptionMap)" :value="item.development_tech_id" :label="item.development_tech_name"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button :icon="Search" type="primary" @click="handleSearch">搜索</el-button>
      </el-form-item>
    </el-form>
  </el-card>

  <el-card style="margin-top: 5px;height: 625px;" shadow="never">
    <el-button :icon="Refresh" @click="doRefresh" circle></el-button>
    <el-button type="primary" @click="changeDialog('add')">新增</el-button>

    <el-table :data="tableData.positionData" stripe height="530">
      <el-table-column type="index" ></el-table-column>
      <el-table-column label="岗位名称" prop="positionName" width="150"></el-table-column>
      <el-table-column label="岗位级别" prop="positionLevelId" width="150">
        <template #default="scope">
          <span v-if="scope.row.positionLevelId!==null&&scope.row.positionLevelId!==undefined">
            {{levelOptionMap[scope.row.positionLevelId]?.position_level_name}}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="薪资下限" prop="salaryMin" sortable width="150"></el-table-column>
      <el-table-column label="薪资上限" prop="salaryMax" sortable width="150"></el-table-column>
      <el-table-column label="开发技术" sortable width="150">
        <template #default="scope">
            <span v-if="scope.row.developmentTechId!==null&&scope.row.developmentTechId!==undefined">
              {{techOptionMap[scope.row.developmentTechId]?.development_tech_name}}
            </span>
        </template>
      </el-table-column>
      <el-table-column label="产能数值" prop="productivity" width="150" sortable></el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button @click="handleShowDetail(scope.row)">详情</el-button>
          <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" @click="handleDel(scope.row)">删除</el-button>
        </template>

      </el-table-column>
    </el-table>

    <el-pagination
        small
        style="font-size: 16px;margin-top: 10px;margin-left: 450px"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNow"
        :page-size="queryInfo.pageSize"
        layout="total, prev, pager, next, jumper"
        :total="tableData.total">
    </el-pagination>
  </el-card>

</template>

<style scoped>

</style>