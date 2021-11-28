<template>
  <div id="search">
    <div>
      <b-button size="sm" pill variant="dark" style="width: 8%; float: left;" @click="redirectToItemSelect()">Back to item type selection</b-button>
      <h2>Movies</h2>
    </div>

    <div class="title-search">
        <label for="title">Search by title: </label>
        <input type="text" id="requestedTitle" name="title">
    </div>
    
    <div class="director-search">
        <label for="director">Search by director: </label>
        <input type="text" id="requestedDirector" name="director">
        <b-button variant="dark" @click="resetMessages(); runSearch()">Submit</b-button>
    </div>

    <p id="invalidInput"></p>
    
    <div>
      <div id="" style="overflow:scroll; height:300px;">
        <v-row justify="center" v-for="libItem in libraryItems" :key="libItem.name">
            <input type="radio" name="item" :value="libItem.isbn">{{ " " + libItem.name }}<br> {{ libItem.creator + ", " + libItem.date + ". " + "isbn " + libItem.isbn }} <br>
        </v-row>
      </div>
    </div>

    <div id="reserve">
      <b-button size="lg" pill variant="dark" @click="resetMessages(); createReserveTransaction(72);">Reserve Item</b-button>
    </div>

    <p id="transaction">
    </p>
  </div>
</template>

<script src="./moviesearching.js">
</script>

<style>
  @import 'styling.css';
</style>