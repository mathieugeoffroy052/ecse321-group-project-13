<template>
  <div id="search">

    <div>
    <b-container>
        <b-row>
            <b-col>
                <br>
                <div>
                    <h2>Room Booking</h2>
                </div>
            </b-col>
        </b-row>
        <b-row>
            <b-col class="shadow p-3 m-3 bg-white rounded">
                <div>
                    <b-calendar id="calendar" v-model="value" locale="en-US" @context="setupRoomTransaction()"></b-calendar>
                </div>
                <br>
                <div>
                  <b-alert show id="status" :variant="alertColour"></b-alert>
                </div>
                <br>
                <div>
                    <b-button id="reserve-button" disabled size="lg" pill variant="dark" @click="createRoomTransaction();">Reserve Room</b-button>
                </div>
            </b-col>
        </b-row>
    </b-container>
    </div>
</div>
</template>

<script src="./roombooking.js">

</script>

<style>
  @import 'styling.css';
</style>