import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = process.env.NODE_ENV === 'production' ? "https://libraryservice-backend-g13.herokuapp.com" : 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'newspapersearch',
    data () {
      return {
        libraryItems: [],
        newLibraryItem: '',
        existingLibraryItem: '',
        errorLibraryItem: '',
        transactions: [],
        newTransaction: '',
        existingTransaction: '',
        errorTransaction: '',
        borrowableItems: [],
        newBorrowableItem: '',
        existingBorrowableItems: [],
        existingBorrowableItem: '',
        errorBorrowableItem: '',
        patrons: [],
        newPatron: '',
        existingPatron: '',
        errorPatron: '',
        response: []
      }
    },

    created: function () {
        AXIOS.get('/newspapers/')
        .then(response => {
            this.libraryItems = response.data
        })
        .catch(e => {
            this.errorLibraryItem = e
        })
        AXIOS.get('/patrons/')
        .then(response => {
            this.patrons = response.data
        })
        .catch(e => {
            this.errorPatron = e
        })
      },

    methods: {
      /*
      * Runs a (filtered) search depending on the inputs of the
      *   "title" and "creator" text field
      * Updating this.libraryItems list (based on the response of the
      *    GET HTTP request) will allow the items displayed on the frontend
      *    in the search results to be updated accordingly
      */
      runSearch : function(){
        var requestedTitle = document.getElementById("requestedTitle").value
        var requestedWriter = document.getElementById("requestedWriter").value
        if(requestedTitle == "" && requestedWriter == "") { // both title and writer field are empty
          AXIOS.get('/newspapers/')
          .then(response => {
              this.libraryItems = response.data
          })
          .catch(e => {
              this.errorLibraryItem = e
          })
          document.getElementById("invalidInput").innerHTML = "Please enter a title or artist"
        } 
        else if(requestedTitle != "" && requestedWriter == "") { // title field is not empty, but writer field is
          console.log("title not empty, writer empty")
          this.errorLibraryItem = null
          var params = {
            title: requestedTitle
          }
          AXIOS.get('/newspapers/title/', {params})
          .then(response => {
            this.libraryItems = response.data
            if(response.data.length == 0){
              document.getElementById("invalidInput").innerHTML = "No newspaper articles found with this title"
            }
          })
          .catch(e => {
            this.errorLibraryItem = e
          })
          if(this.errorLibraryItem != null){ // GET request gave an error
            alert("ERROR");
          } 
        }
        else if(requestedTitle == ""){ // writer field is not empty, but title field is
          console.log("title empty, writer not empty")
          this.errorLibraryItem = null
          var params = {
            writer: requestedWriter
          }
          AXIOS.get('/newspapers/writer/', {params})
          .then(response => {
            this.libraryItems = response.data
            if(response.data.length == 0){
              document.getElementById("invalidInput").innerHTML = "No articles found by this newspaper"
            }
          })
          .catch(e => {
            this.errorLibraryItem = e
          })
          if(this.errorLibraryItem != null){ // GET request gave an error
            alert("ERROR");
          } 
        } 
        else{ // both title and writer field have input in them
          console.log("title and writer not empty")
          this.errorLibraryItem = null
          var params = {
            title : requestedTitle,
            writer: requestedWriter
          }
          AXIOS.get('/newspapers/title/writer/', {params})
          .then(response => {
            this.libraryItems = []
            if(response.data.length == 0) document.getElementById("invalidInput").innerHTML = "No articles found with this title and newspaper"
            else this.libraryItems[0] = response.data
          })
          .catch(e => {
            this.errorLibraryItem = e
            this.libraryItems = []
            document.getElementById("invalidInput").innerHTML = "No articles found with this title and newspaper"
          })
          if(this.errorLibraryItem != null){ // GET request gave an error
            alert("ERROR");
          } 
        }
      },
      /* Reset the messages displayed on the UI */
      resetMessages : function(){
        document.getElementById("invalidInput").innerHTML = ""
      },
      redirectToItemSelect : function(){
        window.location.href='../#/item-select';
      },
      desktopcheck : function(){
        var check = false;
        if(window.innerWidth>500){
            check=true;
        }   
        return check;
      }
    }
  }