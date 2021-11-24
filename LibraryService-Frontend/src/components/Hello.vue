<template>
  <div id="librarySystem">
    <h2>Login</h2>
    <table>
      <tr>
          <!-- <td>UserId</td>
          <td>Login</td> -->
      </tr>
      <tr>
          <td>
              <input type="text" placeholder="Enter UserID">
              <input type="text" placeholder="Enter Password">
          </td>
          <td>
              <button>Login</button>
               
                <div style="position:absolute; right:0px; top:0px;">
                  <button>Create an Account</button>
                </div>

          </td>
      </tr>
    </table>
    <p>
      <span style="color:red">Error: Message text comes here</span>
    </p>
  </div>
</template>

<style>
  #librarySystem {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
   
</style>