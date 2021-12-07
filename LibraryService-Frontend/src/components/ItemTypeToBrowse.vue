<template>
  <div id="search">
   
     <b-container>
       <br>
       <b-row   align-h="center"> 
          <h2>Looking for an Item?</h2>
       </b-row>
        <b-row class="mb-4" align-h="center"> 
          <i>
            <h3>Start by Selecting the Item Type</h3>
          </i>
       </b-row>
      
     <div v-if=" desktopcheck()">
       
        <b-row  align-h="around"  cols="4"  class="mb-5">
        
        <b-button  pill variant="dark" size="lg" squared @click="redirectToBook()">Book</b-button>

        <b-button  pill variant="dark" size="lg"  squared @click="redirectToMovie()">Movie</b-button>

        </b-row>
        <b-row cols="4" align-h="around" class="my-5" >
        
        <b-button  pill variant="dark" size="lg" @click="redirectToMusic()">Music</b-button>
        
        <b-button pill  variant="dark" size="lg" @click="redirectToNewspaper()">Newspaper Article</b-button>
      
        </b-row>
  
     </div>
     <div v-if="!desktopcheck()">
       
        <b-row  align-h="around" class="mb-5">
          <b-button class = "col-10" pill variant="dark" size="lg" squared @click="redirectToBook()">Book</b-button>
        </b-row>
        
        <b-row  align-h="around"  class="mb-5">
          <b-button class = "col-10" pill variant="dark" size="lg"  squared @click="redirectToMovie()">Movie</b-button>
        </b-row>

        <b-row  align-h="around"  class="mb-5">
          <b-button class = "col-10" pill variant="dark" size="lg" @click="redirectToMusic()">Music</b-button>
        </b-row>

        <b-row  align-h="around"  class="mb-5">
          <b-button class = "col-10" pill variant="dark" size="lg" @click="redirectToNewspaper()">Newspaper Article</b-button>
        </b-row>
  
     </div>
     <br>
    </b-container >
   
  </div>
</template>

<script src="./itemselection.js">
</script>

<style>
@import 'styling.css';
</style>