<template>
  <div id="search">



    <div id="reserve">
      <b-button size="lg" pill variant="dark" @click="resetMessages(); createReserveTransaction(72);">Reserve Item</b-button>
    </div>

    <p id="transaction">
    </p>
</template>

<script src="./booksearching.js">
</script>

<style>
  @import 'styling.css';
</style>