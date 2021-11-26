import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import ItemTypeToBrowse from '@/components/ItemTypeToBrowse'
import MusicSearch from '@/components/MusicSearch'
import BookSearch from '@/components/BookSearch'
import MovieSearch from '@/components/MovieSearch'

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
      path: '/book',
      name: 'BookSearch',
      component: BookSearch
    },
    {
      path: '/movie',
      name: 'MovieSearch',
      component: MovieSearch
    }
  ]
})
