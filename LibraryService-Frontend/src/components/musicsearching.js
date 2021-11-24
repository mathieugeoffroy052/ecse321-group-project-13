import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function LibraryItemDTO(name, itemType, date, creator, isViewable, isbn)
{
  this.name = name;
  this.date = date;
  this.itemType = itemType;
  this.creator = creator;
  this.isViewable = isViewable;
  this.isbn = isbn;
}


export default {
    name: 'musicsearch',
    data () {
      return {
        libraryItems: [],
        newLibraryItem: '',
        errorLibraryItem: '',
        response: []
      }
    },

    created: function () {
        AXIOS.get('/music/')
        .then(response => {
            this.libraryItems = response.data
        })
        .catch(e => {
            this.errorLibraryItem = e
        })
      }
  }