<template>
  <div id="itemtypetobrowse">
    <h2>Looking for an item</h2>
      <button @click="redirectToBook()">Book</button>
      <button @click="redirectToMovie()">Movie</button>
      <button @click="redirectToMusic()">Music</button>
      <button @click="redirectToNewspaper()">Newspaper Article</button>
    <p>
    </p>
  </div>
</template>

<script src="./itemselection.js">
</script>

<style>
  #itemtypetobrowse {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #97C3F9;
  }
</style>