<template>
  <div id="moviesearch">
    <h2>Movies</h2>
    <div class="title-search">
        <label for="title">Search by title: </label>
        <input type="text" id="requestedTitle" name="title">
    </div>
    
    <div class="director-search">
        <label for="director">Search by director: </label>
        <input type="text" id="requestedDirector" name="director">
        <button type="director-submit" @click="resetMessages(); runSearch()">Submit</button>
    </div>

    <p id="invalidInput"></p>
    
    <div>
      <div id="" style="overflow:scroll; height:300px;">
        <v-row justify="center" v-for="libItem in libraryItems" :key="libItem.name">
            <input type="radio" name="item" :value="libItem.isbn">{{ " " + libItem.name }}<br> {{ libItem.creator + ", " + libItem.date + ". " + "isbn " + libItem.isbn }} <br>
        </v-row>
      </div>
      <button type="reserve" @click="resetMessages(); createReserveTransaction(7)">Reserve Item</button>
    </div>

    <p id="transaction">
    </p>
  </div>
</template>

<script src="./moviesearching.js">
</script>

<style>
  #moviesearch {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #97C3F9;
  }
  #invalidInput {
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
  
</style>