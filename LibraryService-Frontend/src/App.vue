<template>
  <div id="app">
    <b-navbar toggleable="lg" type="dark" variant="dark" >
      <b-navbar-brand v-if="userID == 'null'" href="./">Home</b-navbar-brand>
      <b-navbar-brand v-if="userID != 'null'"  href="./#/item-select">Home</b-navbar-brand>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="./#/libraryinformation">Library Information</b-nav-item>
          <b-nav-item v-if="userID != 'null' && userID != 0" href="./#/item-select">Item Selection</b-nav-item>
          <b-nav-item v-if="userID != 'null' && userID != 0" href="./#/room">Room Booking</b-nav-item>
          <b-nav-item v-if="userID != 'null' && userID != 0" href="./#/userDashBoard">Dashboard</b-nav-item>
          <b-nav-item v-if="show == 2" href="./#/headlibraryservices">Services</b-nav-item>
          <b-nav-item v-if="show == 1" href="./#/libraryservices">Services</b-nav-item>
        </b-navbar-nav>
      </b-collapse>

      <b-nav-item class="navbar-nav navbar-right" v-if="userID != 'null'" href="./" @click="logout()">Logout</b-nav-item>

    </b-navbar>
    <h1><img src="./assets/figmaBook.png" width="250" height="200">Group 13 Library System</h1>
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
