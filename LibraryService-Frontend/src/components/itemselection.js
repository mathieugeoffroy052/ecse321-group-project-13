import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

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