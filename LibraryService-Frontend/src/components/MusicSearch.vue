<template>
  <div id="search">
    <div class="row">
      <div class="col">
        <br>
        <h2>Music Albums</h2>
      </div>
    </div>

    <b-containter md>
      <b-col class="shadow p-3 m-3 bg-white rounded">
        <b-row>
          <div id="back" style="float: left">
            <b-button
              size="sm"
              pill
              variant="dark"
              @click="redirectToItemSelect()"
              >Back to item type selection</b-button
            >
          </div>
        </b-row>

        <div v-if=" desktopcheck()">
        <b-row>
          <b-col cols="5">
            <label for="title" style="float: right">Search by title: </label>
          </b-col>
          <b-col>
            <input
              type="text"
              id="requestedTitle"
              name="title"
              style="float: left"
            />
          </b-col>
        </b-row>

        <b-row>
          <b-col cols="5">
            <label for="artist" style="float: right"
              >Search by artist:
            </label>
          </b-col>
          <b-col>
            <input
              class="mr-2"
              type="text"
              id="requestedArtist"
              name="artist"
              style="float: left"
            />
            <b-button
              variant="dark"
              style="float: left"
              @click="
                resetMessages();
                runSearch();
              "
              >Submit</b-button
            >
          </b-col>
        </b-row>
        </div>

        <div v-if="!desktopcheck()">
        <b-row>
          <b-col>
            <label for="title">Search by title: </label>
          </b-col>
        </b-row>

        <b-row>
          <b-col>
            <input
              type="text"
              id="requestedTitle"
              name="title"
            />
          </b-col>
        </b-row>

        <b-row>
          <b-col>
            <label for="artist"
              >Search by artist:
            </label>
          </b-col>
        </b-row>

        <b-row class="mb-2">
          <b-col>
            <input
              class="mr-2"
              type="text"
              id="requestedArtist"
              name="artist"
            />
          </b-col>
        </b-row>

        <b-row>
          <b-col>
            <b-button
              variant="dark"
              @click="
                resetMessages();
                runSearch();
              "
              >Submit</b-button
            >
          </b-col>
        </b-row>
        </div>

        <p id="invalidInput"></p>

        <div>
          <div class="shadow p-3 m-3 rounded" id="results" style="overflow-y: scroll; height: 300px">
            <v-row
              justify="center"
              v-for="libItem in libraryItems"
              :key="libItem.name"
            >
              <b-list-group-item id="list-group"><input type="radio" name="item" :value="libItem.isbn" />{{
                " " + libItem.name
              }}<br />
              {{
                libItem.creator +
                ", " +
                libItem.date +
                ". " +
                "isbn " +
                libItem.isbn
              }}
              <br /></b-list-group-item>
            </v-row>
          </div>
        </div>

        <div id="reserve">
          <br />
          <b-button
            size="lg"
            pill
            variant="dark"
            @click="
              resetMessages();
              createReserveTransaction();
            "
            >Reserve Item</b-button
          >
        </div>
        <br>
        <b-alert show id="transaction" :variant="alertColour"></b-alert>
      </b-col>
    </b-containter>
  </div>
</template>

<script src="./musicsearching.js">
</script>

<style>
  @import "styling.css";
</style>