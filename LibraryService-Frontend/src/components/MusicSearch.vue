<template>
  <div id="musicsearch">
    <h2>Music Albums</h2>
    <div class="title-search">
        <label for="title">Search by title: </label>
        <input type="text" id="title" name="title">
        <button type="title-submit">Submit</button>
    </div>
    
    <div class="artist-search">
        <label for="artist">Search by artist: </label>
        <input type="text" id="artist" name="artist">
        <button type="artist-submit">Submit</button>
    </div>
    
    <div>
      <div id="" style="overflow:scroll; height:100px;">
        <v-row justify="center" v-for="libItem in libraryItems" :key="libItem.name">
            <input type="radio">{{ " " + libItem.name }}<br> {{ libItem.creator + ", " + libItem.date + ". " + "isbn " + libItem.isbn }} <br>
        </v-row>
      </div>
      <button type="reserve" @click="createReserveTransaction()">Reserve Item</button>
    </div>

    <p>
      <span style="color:red">Error: Message text comes here</span>
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