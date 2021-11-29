import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import ItemTypeToBrowse from '@/components/ItemTypeToBrowse'
import MusicSearch from '@/components/MusicSearch'
import NewspaperSearch from '@/components/NewspaperSearch'
import BookSearch from '@/components/BookSearch'
import MovieSearch from '@/components/MovieSearch'
import RoomSearch from '@/components/RoomSearch'
import LibrarianView from '@/components/LibrarianView'
import Head from '@/components/Head'
import UserDashBoard from '@/components/UserDashBoard'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
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
      path: '/room',
      name: 'RoomSearch',
      component: RoomSearch
    },
    {
      path: '/libraryservices',
      name: 'LibrarianView',
      component: LibrarianView
    },
    {
      path: '/headlibraryservices',
      name: 'Head',
      component: Head
<<<<<<< HEAD
    },
    {
      path: '/userDashBoard',
      name: 'UserDashBoard',
      component: UserDashBoard
=======

>>>>>>> 6b84731f8b5418203f456657c7c7edaff5d52004
    }

  ]
})
