<template>
<div id="info">
  <b-container v-if="desktopcheck()">
    <b-row >
      <b-col class="shadow p-3 m-3 bg-white rounded">
          <h5>Opening Hours</h5>
        <div class="w-100">
          <b-table hover :items="allOpeningHours"></b-table>
        </div>
      </b-col>
      <b-col class="shadow p-3 m-3 bg-white rounded">
        <h5>Holidays</h5>
        <div class="w-100">
          <b-table hover :items="allHolidays"></b-table>
        </div>
      </b-col>
    </b-row>
  </b-container>
  <b-container v-if="!desktopcheck()" class="center p-3">
      <b-row class="shadow p-3 m-3 bg-white rounded">
          <h5>Opening Hours</h5>
        <div class="w-100">
          <b-table hover :items="allOpeningHours"></b-table>
        </div>
      </b-row>
      <b-row class="shadow p-3 m-3 bg-white rounded">
        <h5>Holidays</h5>
        <div class="w-100">
          <b-table hover :items="allHolidays"></b-table>
        </div>
      </b-row>
  </b-container>
</div>
</template>

<script src="./libraryinformation.js"></script>

<style >
@import "styling.css";
</style>
