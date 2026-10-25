<script setup>
import { ArrowRight, ArrowUpRight, ChartNoAxesCombined, Database, Film, MapPin, ShoppingBag } from '@lucide/vue'
import { resourceGroups, resources } from '../data/resources'

const groupIcons = { Catálogo: Film, Direcciones: MapPin, Ventas: ShoppingBag, Vistas: ChartNoAxesCombined }
</script>

<template>
  <div class="landing-page">
    <div class="eyebrow"><span class="eyebrow-dot"></span> PANEL DE ADMINISTRACIÓN</div>
    <section class="hero-section">
      <div class="hero-copy">
        <h1>Tu base de datos.<br /><span>Todo en su sitio.</span></h1>
        <p>Gestiona el catálogo de Sakila desde un único espacio. Explora registros, consulta detalles y mantén tus datos al día.</p>
        <RouterLink class="button button-primary hero-cta" :to="{ name: 'resource', params: { resource: 'actors' } }">
          Explorar registros <ArrowRight :size="16" />
        </RouterLink>
      </div>
      <div class="hero-graphic" aria-hidden="true">
        <div class="graphic-orbit orbit-one"></div><div class="graphic-orbit orbit-two"></div>
        <div class="graphic-card graphic-back"><Database :size="22" /><span>sakila_db</span><small>MySQL</small></div>
        <div class="graphic-card graphic-front"><strong>Backend Spring Boot</strong><small>API REST · 127.0.0.1:8080</small></div>
        <div class="graphic-pill"><span>●</span> 23 recursos</div>
      </div>
    </section>

    <section class="overview-heading">
      <div><span class="eyebrow muted-eyebrow">NAVEGACIÓN</span><h2>Explora tu espacio</h2></div>
      <div class="resource-total"><strong>{{ resources.length }}</strong> recursos disponibles</div>
    </section>

    <section class="group-grid">
      <RouterLink
        v-for="(group, index) in resourceGroups"
        :key="group.name"
        class="group-card"
        :class="`group-card-${index}`"
        :to="{ name: 'resource', params: { resource: group.resources[0].slug } }"
      >
        <div class="group-card-top">
          <span class="group-icon"><component :is="groupIcons[group.name]" :size="19" /></span>
          <span class="group-arrow"><ArrowUpRight :size="16" /></span>
        </div>
        <h3>{{ group.name }}</h3>
        <p>{{ group.resources.length }} {{ group.resources.length === 1 ? 'recurso' : 'recursos' }} para consultar y gestionar</p>
        <div class="group-card-bottom">
          <span>{{ group.resources.slice(0, 2).map((item) => item.name).join(' · ') }}</span>
          <span class="group-count">{{ String(group.resources.length).padStart(2, '0') }}</span>
        </div>
      </RouterLink>
    </section>

    <section class="landing-note">
      <span class="note-icon"><Database :size="16" /></span>
      <span>Las vistas de informes están disponibles en <strong>modo de solo lectura.</strong></span>
      <span class="note-separator"></span>
      <span class="note-live">API REST · 127.0.0.1:8080</span>
    </section>

    <footer class="page-footer"><span>SAKILA DATABASE STUDIO</span><span>Hecho para trabajar con claridad.</span></footer>
  </div>
</template>
