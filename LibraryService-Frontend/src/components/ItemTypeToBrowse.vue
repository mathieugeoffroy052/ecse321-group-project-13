<template>
  <div id="search">
   
     <b-container >
       <b-row class="my-5" align-h="center" > 
    <h2>Looking for an Item?</h2>
       </b-row>
       
     
     <div>
       
        <b-row  align-h="around"  cols="4"  class="mb-5">
        
        <b-button  pill variant="outline-secondary" size="lg" squared @click="redirectToBook()">Book</b-button>

        <b-button  pill variant="outline-secondary" size="lg"  squared @click="redirectToMovie()">Movie</b-button>

        </b-row>
        <b-row cols="4" align-h="around" class="my-5" >
        
        <b-button  pill variant="outline-secondary" size="lg" @click="redirectToMusic()">Music</b-button>
       
        
        <b-button pill  variant="outline-secondary" size="lg" @click="redirectToNewspaper()">Newspaper Article</b-button>
      
        </b-row>
   
       <b-row class="my-5" align-h="end" > 
    <h3>Start by Selecting the Item Type</h3>
       </b-row>
     </div>
      </b-container >
   
  </div>
</template>

<script src="./itemselection.js">
</script>

<style>
@import 'styling.css';
</style>