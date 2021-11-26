<template>
  <div id="musicsearch">
    <h2>Music Albums</h2>
    <div class="title-search">
        <label for="title">Search by title: </label>
        <input type="text" id="requestedTitle" name="title">
    </div>
    
    <div class="artist-search">
        <label for="artist">Search by artist: </label>
        <input type="text" id="requestedArtist" name="artist">
        <button type="submit" @click="resetMessages(); runSearch(); ">Submit</button>
    </div>

    <p id="errorMessage"></p>
    
    <div>
      <div id="" style="overflow:scroll; height:300px;">
        <v-row justify="center" v-for="libItem in libraryItems" :key="libItem.name">
            <input type="radio" name="item" :value="libItem.isbn">{{ " " + libItem.name }}<br> {{ libItem.creator + ", " + libItem.date + ". " + "isbn " + libItem.isbn }} <br>
        </v-row>
        <p id="noItemFound"></p>
      </div>
      <button type="reserve" @click="resetMessages(); createReserveTransaction(72);">Reserve Item</button>
    </div>

    <p id="transaction"></p>
  </div>
</template>

<script src="./musicsearching.js">
</script>

<style>
  #musicsearch {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #97C3F9;
  }
  #errorMessage {
    font-family: 'Avenir', Helvetica, Arial, serif;
    font-size: 24px;
    font-style: italic;
    color: #0E0E47;
    background: #97C3F9;
  }
  #transaction {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    font-size: 30px;
    color: #2c3e50;
    background: #97C3F9;
  }
  #noItemFound {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    font-size: 35px;
    font-style: italic;
    color: #000000;
    background: #97C3F9;
  }
</style>