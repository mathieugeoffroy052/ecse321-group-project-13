<template>
  <div id="app">
    <b-navbar toggleable="lg" type="dark" variant="info">
      <b-navbar-brand href="./">Home</b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="./#/item-select">Item Selection</b-nav-item>
          <b-nav-item href="./#/room">Room Booking</b-nav-item>
          <b-nav-item href="./#/userDashBoard">Dashboard</b-nav-item>
          <b-nav-item id="services" :href="userLink"></b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <h1><img src="./assets/figmaBook.png" width="250" height="200">Group 13 Library System</h1>
    <router-view></router-view>
  </div>
</template>
<script src="./app.js">
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0px;
}
</style>
