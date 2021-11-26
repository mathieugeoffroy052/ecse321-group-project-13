<template>
    <div id ="libraryservices">
        <h2>Library Services</h2>
        <div>
            <b-tabs content-class="mt-3" align="center">
                <b-tab title="First" active>
                    <p>I'm the first tab</p>
                </b-tab>
                <b-tab title="Second">
                    <p>I'm the second tab</p>
                </b-tab>
                <b-tab title="Disabled" disabled>
                    <p>I'm a disabled tab!</p>
                </b-tab>
            </b-tabs>
        </div>
</template>

<script src="librarianview.js">
</script>

<style>
  #libraryservices {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #97C3F9;
  }
</style>