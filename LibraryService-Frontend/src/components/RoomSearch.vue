<template>
  <div id="search">

    <div>
    <b-container>
        <b-row>
            <b-col >
                <b-calendar id="calendar" v-model="value" locale="en-US" @context="setupRoomTransaction()"></b-calendar>
            </b-col>
        </b-row>
    </b-container>
    </div>

    <div>
        <p id="status"></p>
    </div>


    <div id="reserve">
      <b-button id="reserve-button" disabled size="lg" pill variant="dark" @click="createRoomTransaction();">Reserve Room</b-button>
    </div>

    <p id="transaction">
    </p>
</div>
</template>

<script src="./roombooking.js">

</script>

<style>
  @import 'styling.css';
</style>