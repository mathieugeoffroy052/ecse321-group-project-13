import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = process.env.NODE_ENV === 'production' ? "https://libraryservice-backend-g13.herokuapp.com" : 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'moviesearch',
    data () {
      return {
        response: []
      }
    },

    /**
     * This is used to redirect the user's to the specifc search pages based on the item tehy want to find.
     */
    methods: {
      redirectToBook: function () {
        window.location.href='../#/book';
      },
      redirectToMusic: function () {
        window.location.href='../#/music';
      },
      redirectToMovie: function () {
        window.location.href='../#/movie';
      },
      redirectToNewspaper: function () {
        window.location.href='../#/newspaper';
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