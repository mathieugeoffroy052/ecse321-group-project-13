<template>
  <div id="search">
    <div class="row">
      <div class="col">
        <br>
        <h2>Newspaper Articles</h2>
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
            <label for="writer" style="float: right"
              >Search by writer:
            </label>
          </b-col>
          <b-col>
            <input
              class="mr-2"
              type="text"
              id="requestedWriter"
              name="writer"
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
      </b-col>
    </b-containter>
  </div>
</template>

<script src="./newspapersearching.js">
</script>

<style>
@import "styling.css";
</style>