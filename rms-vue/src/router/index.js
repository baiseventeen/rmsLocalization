import { createRouter, createWebHistory } from "vue-router";

const routes = [
    {
        path: "/",
        redirect: "/login"
    },
    {
        path: "/login",
        component: () => import("../views/login/Login.vue"),
        meta: {
            title: '登录界面'
        }
    },
    {
        path: "/home",
        component: () => import("../components/layout/CommonLayout.vue"),
        children: [
            {
                path: "/modeling",
                component: () => import("../views/modeling/modelingLayout/SecondLayout.vue"),
                redirect: '/developerManage',
                meta: {
                    title: "产能建模"
                },
                children: [
                    {
                        path: "/",
                        redirect: "/developerManage"
                    },
                    {
                        path: "/developerManage",
                        component: () => import("../views/modeling/DeveloperManage.vue"),
                    },
                    {
                        path: "/groupManage",
                        component: () => import("../views/modeling/GroupManage.vue"),
                    },
                    {
                        path: "/skillManage",
                        component: () => import("../views/modeling/SkillManage.vue"),
                    },
                    {
                        path: "/resourceMonitoring",
                        component: () => import("../views/modeling/ResourceMonitoring.vue"),
                    },
                    {
                        path: "/performanceAnalysis",
                        component: () => import("../views/modeling/PerformanceAnalysis.vue"),
                    },
                    {
                        path: "/positionManage",
                        component: () => import("../views/modeling/PositionManage.vue"),
                    }
                ]
            },
            {
                path: "/schedule",
                name: "schedule",
                meta: {
                    title: '排期系统'
                },
                redirect: '/projectManage',
                component: () => import("../views/schedule/scheduleLayout/ScheduleLayout.vue"),
                children: [
                    {
                        path: '/projectManage',
                        name: 'projectManage',
                        component: () => import("../views/schedule/ProjectManage.vue")
                    },
                    {
                        path: '/projectSystemManage',
                        name: 'projectSystemManage',
                        component: () => import("../views/schedule/ProjectSystemManage.vue")
                    },
                    {
                        path: '/projectHistory',
                        name: 'projectHistory',
                        component: () => import("../views/schedule/ProjectHistory.vue")
                    },
                    {
                        path: '/projectSystemHistory',
                        name: 'projectSystemHistory',
                        component: () => import("../views/schedule/ProjectSystemHistory.vue")
                    },
                    {
                        path: '/scheduleGenerate',
                        name: 'scheduleGenerate',
                        component: () => import("../views/schedule/ScheduleGenerate.vue")
                    },
                    {
                        path: '/scheduleProjectView',
                        name: 'scheduleProjectView',
                        component: () => import("../views/schedule/ScheduleProjectView.vue")
                    },
                    {
                        path: '/scheduleDeveloperView',
                        name: 'scheduleDeveloperView',
                        component: () => import("../views/schedule/ScheduleDeveloperView.vue")
                    },
                    {
                        path: '/investment',
                        name: 'investment',
                        component: () => import("../views/schedule/Investment.vue")
                    },
                    {
                        path: '/alterationRequest',
                        name: 'alterationRequest',
                        component: () => import("../views/schedule/AlterationRequest.vue")
                    },
                    {
                        path: '/alterationProcessing',
                        name: 'alterationProcessing',
                        component: () => import("../views/schedule/AlterationProcessing.vue")
                    },{
                        path: '/alterationManage',
                        name: 'alterationManage',
                        component: () => import("../views/schedule/AlterationManage.vue")
                    },

                ]
            },
        ]

    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router