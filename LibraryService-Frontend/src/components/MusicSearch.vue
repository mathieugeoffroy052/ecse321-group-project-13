<template>
  <div id="musicsearch">
    <h2>Music Albums</h2>
    <div class="title-search">
        <label for="title">Search by title: </label>
        <input type="text" id="requestedTitle" name="title">
        <button type="title-submit" @click="searchFilteredByTitle()">Submit</button>
    </div>
    
    <div class="artist-search">
        <label for="artist">Search by artist: </label>
        <input type="text" id="requestedArtist" name="artist">
        <button type="artist-submit" @click="searchFilteredByArtist(getElementById(requestedArtist).value)">Submit</button>
    </div>
    
    <div>
      <div id="" style="overflow:scroll; height:100px;">
        <v-row justify="center" v-for="libItem in libraryItems" :key="libItem.name">
            <input type="radio" name="item" :value="libItem.isbn">{{ " " + libItem.name }}<br> {{ libItem.creator + ", " + libItem.date + ". " + "isbn " + libItem.isbn }} <br>
        </v-row>
      </div>
      <button type="reserve" @click="createReserveTransaction(7)">Reserve Item</button>
    </div>

    <p id="transaction">
    </p>
  </div>
</template>

<script src="./musicsearching.js">
</script>

<style>
  #musicsearch {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
</style>