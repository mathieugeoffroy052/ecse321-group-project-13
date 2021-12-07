<template>
  <div id="app" >
    <b-navbar toggleable="lg" type="dark" variant="dark" >
      <b-navbar-brand v-if="userID == 'null' || userID == null" href="./">Home</b-navbar-brand>
      <b-navbar-brand v-if="userID != 'null' && userID != null"  href="./#/item-select">Home</b-navbar-brand>
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="./#/libraryinformation">Library Information</b-nav-item>
          <b-nav-item v-if="userID != 'null' && userID != 0 && userID != null" href="./#/item-select">Item Selection</b-nav-item>
          <b-nav-item v-if="userID != 'null' && userID != 0 && userID != null" href="./#/room">Room Booking</b-nav-item>
          <b-nav-item v-if="userID != 'null' && userID != 0 && userID != null" href="./#/userDashBoard">Dashboard</b-nav-item>
          <b-nav-item v-if="show == 2 && desktopcheck()" href="./#/headlibraryservices">Services</b-nav-item>
          <b-nav-item v-if="show == 1 && desktopcheck()" href="./#/libraryservices">Services</b-nav-item>
        </b-navbar-nav>
      </b-collapse>

      <b-nav-item class="navbar-nav navbar-right" v-if="userID != 'null' && userID != null" href="./" @click="logout()">Logout</b-nav-item>

    </b-navbar>
    <h1 v-if="desktopcheck()">
      <img  src="./assets/figmaBook.png" width="250" height="200">
      Group 13 Library System
    </h1>
    <h3 v-if="!desktopcheck()">
      <img  src="./assets/figmaBook.png" width="100" height="80">
      Group 13 Library System
    </h3>
    <router-view></router-view>
  </div>
</template>
<script src="./loadapp.js">
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
