<template>
  <div id="search">
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
@import 'styling.css';
</style>