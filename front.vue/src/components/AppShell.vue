<script setup>
import { computed, ref } from 'vue'
import { useRoute } from 'vue-router'
import {
  ArrowUpRight,
  ChartNoAxesCombined,
  ChevronDown,
  Database,
  Film,
  Home,
  MapPin,
  Menu,
  ShoppingBag,
  X,
} from '@lucide/vue'
import { resourceGroups, resources } from '../data/resources'

const route = useRoute()
const mobileOpen = ref(false)
const iconMap = { Film, MapPin, ShoppingBag, ChartNoAxesCombined }
const currentResource = computed(() => resources.find((item) => item.slug === route.params.resource))
</script>

<template>
  <div class="app-layout">
    <button v-if="mobileOpen" class="mobile-scrim" aria-label="Cerrar menú" @click="mobileOpen = false"></button>

    <aside class="sidebar" :class="{ 'sidebar-open': mobileOpen }">
      <RouterLink class="brand" to="/" @click="mobileOpen = false">
        <span class="brand-mark"><Film :size="19" :stroke-width="2.2" /></span>
        <span class="brand-copy"><strong>sakila</strong><small>DATABASE STUDIO</small></span>
      </RouterLink>

      <div class="workspace-chip">
        <span class="workspace-avatar">S</span>
        <span class="workspace-info"><strong>Proyecto Sakila</strong><small>MySQL · Local</small></span>
        <ChevronDown :size="15" class="muted-icon" />
      </div>

      <nav class="side-nav" aria-label="Navegación principal">
        <span class="nav-caption">ESPACIO DE TRABAJO</span>
        <RouterLink class="nav-link" to="/" @click="mobileOpen = false">
          <Home :size="17" /><span>Inicio</span>
        </RouterLink>

        <section v-for="group in resourceGroups" :key="group.name" class="nav-group">
          <div class="nav-section-heading">
            <component :is="iconMap[group.icon]" :size="15" />
            <span>{{ group.name }}</span>
            <span class="nav-count">{{ group.resources.length }}</span>
          </div>
          <RouterLink
            v-for="item in group.resources"
            :key="item.slug"
            class="nav-link nav-resource"
            :to="{ name: 'resource', params: { resource: item.slug } }"
            :class="{ 'is-active': route.params.resource === item.slug }"
            @click="mobileOpen = false"
          >
            <span class="nav-dot"></span>
            <span>{{ item.name }}</span>
            <span v-if="item.readOnly" class="read-only-mark" title="Solo lectura">R</span>
          </RouterLink>
        </section>
      </nav>

      <div class="sidebar-footer">
        <span>API REST</span>
        <span class="connection-url">127.0.0.1:8080</span>
      </div>
    </aside>

    <div class="main-column">
      <header class="topbar">
        <button class="icon-button menu-toggle" aria-label="Abrir menú" @click="mobileOpen = !mobileOpen">
          <Menu v-if="!mobileOpen" :size="19" /><X v-else :size="19" />
        </button>
        <div class="breadcrumbs">
          <span>sakila</span><span class="crumb-separator">/</span>
          <span>{{ currentResource?.group ?? 'Workspace' }}</span>
          <template v-if="currentResource">
            <span class="crumb-separator">/</span><strong>{{ currentResource.name }}</strong>
          </template>
          <template v-else><span class="crumb-separator">/</span><strong>Inicio</strong></template>
        </div>
        <a class="topbar-link" href="http://127.0.0.1:8080" target="_blank" rel="noreferrer">
          API <ArrowUpRight :size="14" />
        </a>
      </header>
      <main class="main-content">
        <RouterView />
      </main>
    </div>
  </div>
</template>
