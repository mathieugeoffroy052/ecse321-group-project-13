<template>
  <div id="search">
    <div id="back">
        <b-button size="sm" pill variant="dark" style="float: left;" @click="redirectToItemSelect()">Back to item type selection</b-button>
    </div>
    <div class="row">
      <div class="col-lg-10 center-block">
        <h2>Music Albums</h2>
      </div>
    </div>

    <div class="row">
      <div class="col-5">
        <label for="title" style="float: right;">Search by title: </label>
      </div>
      <div class="col">
        <input type="text" id="requestedTitle" name="title" style="float: left;">
      </div>
    </div>

    <div class="row">
      <div class="col-5">
        <label for="artist" style="float: right;">Search by artist: </label>
      </div>
      <div class="col-3">
        <input type="text" id="requestedArtist" name="artist" style="float: left;">
      </div>
      <div class="col">
        <b-button variant="dark" style="float: left;" @click="resetMessages(); runSearch()">Submit</b-button>
      </div>
    </div>
    
    <p id="invalidInput"></p>
    
    <div>
      <div style="overflow:scroll; height:300px;">
        <v-row justify="center" v-for="libItem in libraryItems" :key="libItem.name">
            <input type="radio" name="item" :value="libItem.isbn">{{ " " + libItem.name }}<br> {{ libItem.creator + ", " + libItem.date + ". " + "isbn " + libItem.isbn }} <br>
        </v-row>
      </div>
    </div>

    <div id="reserve">
      <b-button size="lg" pill variant="dark" @click="resetMessages(); createReserveTransaction(72);">Reserve Item</b-button>
    </div>


    <p id="transaction"></p>
  </div>
</template>

<script src="./musicsearching.js">
</script>

<style>
  @import 'styling.css';
</style>