<template>
  <div id="musicsearch">
    <div>
      <b-button size="sm" pill variant="dark" style="width: 8%; float: left;" @click="redirectToItemSelect()">Back to item type selection</b-button>
      <h2>Music Albums</h2>
    </div>

    <div>
      <div class="title-search">
        <label>Search by title: </label>
        <input type="text" id="requestedTitle" name="title">
      </div>
    </div>
    
    <div class="artist-search">
        <label>Search by artist: </label>
        <input type="text" id="requestedArtist" name="artist">
        <b-button variant="dark" @click="resetMessages(); runSearch()">Submit</b-button>
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
  #musicsearch {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    font-size: 20px;
    color: #2c3e50;
    background: #97C3F9;
  }
  #invalidInput {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    font-size: 24px;
    font-style: italic;
    color: #0E0E47;
    background: #97C3F9;
  }
  #reserve{
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    font-size: 30px;
    background: #ffffff;
  }
  #transaction {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    font-size: 30px;
    color: #2c3e50;
    background: #97C3F9;
  }
</style>