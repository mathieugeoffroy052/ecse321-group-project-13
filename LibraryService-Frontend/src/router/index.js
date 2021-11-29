import Vue from 'vue'
import Router from 'vue-router'

import Login from '@/components/Login'
import CreateAccount from '@/components/CreateAccount'
import ItemTypeToBrowse from '@/components/ItemTypeToBrowse'
import MusicSearch from '@/components/MusicSearch'
import NewspaperSearch from '@/components/NewspaperSearch'
import BookSearch from '@/components/BookSearch'
import MovieSearch from '@/components/MovieSearch'
import LibrarianView from '@/components/LibrarianView'
import RoomSearch from '@/components/RoomSearch'
import Head from '@/components/Head'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/CreateAccount',
      name: 'CreateAccount',
      component: CreateAccount
    },
    {
      path: '/item-select',
      name: 'ItemTypeToBrowse',
      component: ItemTypeToBrowse
    },
    {
      path: '/music',
      name: 'MusicSearch',
      component: MusicSearch
    },
    {
      path: '/newspaper',
      name: 'NewspaperSearch',
      component: NewspaperSearch
    },
    {
      path: '/book',
      name: 'BookSearch',
      component: BookSearch
    },
    {
      path: '/movie',
      name: 'MovieSearch',
      component: MovieSearch
    },
    {
      path: '/libraryservices',
      name: 'LibrarianView',
      component: LibrarianView
    },
    {
      path: '/room',
      name: 'RoomSearch',
      component: RoomSearch
    },
    {
      path: '/headlibraryservices',
      name: 'Head',
      component: Head
    }
  ]
})
