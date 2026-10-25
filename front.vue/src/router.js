import { createRouter, createWebHistory } from 'vue-router'
import AppShell from './components/AppShell.vue'
import LandingPage from './views/LandingPage.vue'
import ResourcePage from './views/ResourcePage.vue'

export default createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      component: AppShell,
      children: [
        { path: '', name: 'home', component: LandingPage },
        { path: ':resource', name: 'resource', component: ResourcePage },
      ],
    },
    { path: '/:pathMatch(.*)*', redirect: '/' },
  ],
})
