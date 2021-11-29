<template>
  <div id="search">
    <h2>Looking for an item</h2>
    <div>
        <b-button pill variant="dark" style="width: 8%; float: center;" @click="redirectToBook()">Book</b-button>
        <b-button pill variant="dark" style="width: 8%; float: center;" @click="redirectToMovie()">Movie</b-button>
        <b-button pill variant="dark" style="width: 8%; float: center;" @click="redirectToMusic()">Music</b-button>
        <b-button pill variant="dark" style="width: 14%; float: center;" @click="redirectToNewspaper()">Newspaper Article</b-button>
    </div>
  </div>
</template>

<script src="./itemselection.js">
</script>

<style>
@import 'styling.css';
</style>