<template>
  <div id="booksearch">
    <h2>Books</h2>
    <div class="title-search">
        <label for="title">Search by title: </label>
        <input type="text" id="requestedTitle" name="title">
        <button type="title-submit" @click="searchFilteredByTitle()">Submit</button>
    </div>
    
    <div class="author-search">
        <label for="author">Search by author: </label>
        <input type="text" id="requestedAuthor" name="author">
        <button type="author-submit" @click="searchFilteredByAuthor()">Submit</button>
    </div>
    
    <div>
      <div id="" style="overflow:scroll; height:300px;">
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

<script src="./booksearching.js">
</script>

<style>
  #booksearch {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #97C3F9;
  }
  #transaction {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    font-size: 30px;
    color: #2c3e50;
    background: #97C3F9;
  }
</style>