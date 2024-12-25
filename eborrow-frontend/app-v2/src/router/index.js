import { createRouter, createWebHistory } from 'vue-router'

// only necessary if using eager loading:
// import LoginView from '../views/LoginView.vue'
// import RegisterView from '../views/RegisterView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'login',
      // component: LoginView, // eager loading
      component: () => import('../views/LoginView.vue') // lazy loading
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/RegisterView.vue')
    },
    {
      path: '/account',
      name: 'account',
      component: () => import('../views/ProfileView.vue')
    },
    {
      path: '/account/edit',
      name: 'edit-account',
      component: () => import('../views/ProfileEditView.vue')
    }
  ],
})

export default router
