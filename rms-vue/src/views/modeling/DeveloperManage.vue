<script setup>
import {getCurrentInstance, onMounted, reactive,inject} from "vue";
import {Search,Refresh} from "@element-plus/icons-vue";
import {ElNotification} from "element-plus";

const {proxy} = getCurrentInstance()

const doRefresh = inject("reload")

  const queryInfo = reactive({
    pageNow : 1,
    pageSize: 10,
    name: null,
    startBirthDate: null,
    endBirthDate: null,
    startOnBoardDate: null,
    endOnBoardDate: null,

  })

  const tableData = reactive({
    total: 0,
    developerData: []
  })

  const handleSearch = ()=>{
    proxy.$http({
      url: "/modeling/developerManage/page",
      method: "post",
      data: queryInfo
    }).then((res)=>{
      tableData.total=res.data.data.totalRows
      tableData.developerData=res.data.data.rowData
      // console.log(res)
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
        dialogConfig.title="新增开发人员";
        break;
      }
      case "edit": {
        dialogConfig.type="edit";
        dialogConfig.disable=false;
        dialogConfig.title="修改开发人员信息";
        break;
      }
      case "show": {
        dialogConfig.type="show";
        dialogConfig.disable=true;
        dialogConfig.title="开发人员详情";
        break;
      }
    }
  }

  const newDeveloper = reactive({
    id: null,
    name: null,
    birthday: null,
    phone: null,
    onboardingTime: null,
    college: null,
    educationBackground: null,
    position: null,
    salary: null,
    groupId: null
  })

  const clearNewDeveloper = ()=>{
    Object.assign(newDeveloper, {
      id: null,
      name: null,
      birthday: null,
      phone: null,
      onboardingTime: null,
      college: null,
      educationBackground: null,
      position: null,
      salary: null,
      groupId: null
    });
  }

  const handleSummit = ()=>{
    proxy.$http({
      url: "/modeling/developerManage/"+(dialogConfig.type==="add"?"add":"update"),
      method: dialogConfig.type==="add"?"post":"put",
      data: newDeveloper,
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

  const handleShowDetail = (row)=>{
    Object.assign(newDeveloper,row)
    changeDialog('show')
  }

  const handleEdit = (row)=>{
    Object.assign(newDeveloper,row)
    changeDialog('edit')
  }

  const handleDel = (row)=>{
    proxy.$http({
      url: "/modeling/developerManage/"+row.id,
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
  })
</script>

<template>

  <el-dialog v-model="dialogConfig.visible" style="width: fit-content" :title="dialogConfig.title" @close="clearNewDeveloper">
    <el-form label-position="left" label-width="auto" :disabled="dialogConfig.disable">
      <el-form-item label="姓名">
        <el-input v-model="newDeveloper.name" placeholder="请输入姓名" clearable/>
      </el-form-item>
      <el-form-item label="生日">
        <el-date-picker v-model="newDeveloper.birthday" clearable/>
      </el-form-item>
      <el-form-item label="手机号码">
        <el-input v-model="newDeveloper.phone" clearable />
      </el-form-item>
      <el-form-item label="入职日期">
        <el-date-picker v-model="newDeveloper.onboardingTime" clearable/>
      </el-form-item>
      <el-form-item label="毕业院校">
        <el-input v-model="newDeveloper.college" clearable/>
      </el-form-item>
      <el-form-item label="学历">
        <el-select
            v-model="newDeveloper.educationBackground"
            allow-create
            filterable
            placeholder=""
            clearable
        >
          <el-option label="专科" value="专科"/>
          <el-option label="本科" value="本科"/>
          <el-option label="硕士" value="硕士"/>
          <el-option label="博士" value="博士"/>
        </el-select>
      </el-form-item>
      <el-form-item label="岗位">
        <el-select disabled placeholder="">

        </el-select>
      </el-form-item>
      <el-form-item label="月薪">
        <el-input v-model="newDeveloper.salary" clearable/>
      </el-form-item>
      <el-form-item label="所属小组">
        <el-select disabled placeholder="">

        </el-select>
      </el-form-item>
    </el-form>
    <template v-if="!dialogConfig.disable">
      <el-button type="primary" @click="handleSummit">确认</el-button>
      <el-button @click="dialogConfig.visible=false">取消</el-button>
    </template>

  </el-dialog>

  <el-card style="height: 75px;" shadow="never">
    <el-form :inline="true" :model="queryInfo" >
      <el-form-item label="开发人员姓名" style="width: max-content">
        <el-input v-model="queryInfo.name" placeholder="请输入姓名" clearable style="width: 120px;"/>
      </el-form-item>
      <el-form-item label="生日范围" style="width: max-content">
        <el-date-picker
          v-model="queryInfo.startBirthDate"
          placeholder="开始日期"
          clearable
          style="width: 150px;"
        />
        <span style="margin-left: 5px;margin-right: 5px;">-</span>
        <el-date-picker
          v-model="queryInfo.endBirthDate"
          placeholder="结束日期"
          clearable
          style="width: 150px;"
        />
      </el-form-item>
      <el-form-item label="入职日期范围" style="width: max-content">
        <el-date-picker
            v-model="queryInfo.startOnBoardDate"
            placeholder="开始日期"
            clearable
            style="width: 150px;"
        />
        <span style="margin-left: 5px;margin-right: 5px;">-</span>
        <el-date-picker
            v-model="queryInfo.endOnBoardDate"
            placeholder="结束日期"
            clearable
            style="width: 150px;"
        />
      </el-form-item>
      <el-form-item>
        <el-button :icon="Search" type="primary" @click="handleSearch">搜索</el-button>
      </el-form-item>
    </el-form>
  </el-card>

  <el-card style="margin-top: 5px;height: 625px;" shadow="never">
    <el-button :icon="Refresh" @click="doRefresh" circle></el-button>
    <el-button type="primary" @click="changeDialog('add')">新增</el-button>
    <el-button type="success">导入</el-button>
    <el-button type="warning">导出</el-button>

    <el-table :data="tableData.developerData" stripe height="530">
      <el-table-column type="index" ></el-table-column>
<!--      <el-table-column label="开发人员id" prop="id"></el-table-column>-->
      <el-table-column label="姓名" prop="name" width="150"></el-table-column>
      <el-table-column label="生日" prop="birthday" sortable width="150"></el-table-column>
      <el-table-column label="联系方式" prop="phone" width="150"></el-table-column>
      <el-table-column label="入职日期" prop="onboardingTime" sortable width="150"></el-table-column>
      <el-table-column label="学历" prop="educationBackground" width="150"></el-table-column>
      <el-table-column label="岗位" prop="position" width="150"></el-table-column>
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