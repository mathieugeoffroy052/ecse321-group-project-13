<template>
  <div id="Login">
    <section class="vh-100" style="background-color: #FFFFFF;">
      <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
          <div class="col-12 col-md-8 col-lg-6 col-xl-5">
            <div class="card shadow-2-strong" style="border-radius: 1rem;">
              <div class="card-body p-5 text-center">

                <h3 class="mb-5">Create an Account</h3>

                <div class="form-outline mb-4">
                  <input type="firstName" id="typefirstNameX-2" class="form-control form-control-lg" />
                  <label class="form-label" for="typefirstNameX-2">UserID</label>
                </div>

                <div class="form-outline mb-4">
                  <input type="lastName" id="lastNameX-2" class="form-control form-control-lg" />
                  <label class="form-label" for="typeLastNameX-2">Last Name</label>
                </div>

                <div class="form-outline mb-4">
                  <input type="address" id="typeAddressX-2" class="form-control form-control-lg" />
                  <label class="form-label" for="typeAddressX-2">Address</label>
                </div>

                <div class="form-outline mb-4">
                  <input type="email" id="typeEmailX-2" class="form-control form-control-lg" />
                  <label class="form-label" for="typeEmailX-2">Email</label>
                </div>

                <div class="form-outline mb-4">
                  <input type="password" id="typePasswordX-2" class="form-control form-control-lg" />
                  <label class="form-label" for="typePasswordX-2">Password</label>
                </div>

                <div class="form-outline mb-4">
                  <input type="confirmPassword" id="typeconfirmPasswordX-2" class="form-control form-control-lg" />
                  <label class="form-label" for="typeconfirmPasswordX-2">Confirm Password</label>
                </div>

                <button class="btn btn-primary btn-lg btn-block" type="submit">Create Account</button>

                <hr class="my-4">

              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template> 

<style>
  #librarySystem {
    position: absolute;
    width: 1433px;
    height: 831px;

  }
</style>
<style>
  #libraryTitle {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 0px;

    position: absolute;
    width: 540px;
    height: 98px;
    left: 50px;
    top: 59px;
  }
  </style>

  <style>
    #lightblueBox {
      position: absolute;
      width: 519px;
      height: 414px;
      left: 151px;
      top: 294px;

      background: rgba(255, 255, 255, 0.6);
      border: 1px solid #000000;
      box-sizing: border-box;
    }
  </style>

  <style>
    #whiteseperator {
      position: absolute;
      width: 674px;
      height: 866px;
      left: 766px;
      top: 0px;
    }
  </style>

  <style>
    #logoBook {
      position: absolute;
      width: 115.43px;
      height: 114.04px;
      left: calc(50% - 115.43px/2 - 641.26px);
      top: calc(50% - 114.04px/2 - 353.48px);

      transform: rotate(0.48deg);
    }
  </style>



  <!--
  <template>
  <div id="CreateAccount">
    <div style="position:absolute; left:0px; top:0px;">
        <h2>CreateAccount</h2>
      </div>
    
    <table>
      
      <tr>
          <td>
              
              <input type="text" placeholder="First Name"> 
              <input type="text" placeholder="Last Name">
              
                
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
    position: absolute;
    width: 1433px;
    height: 831px;

  }
</style>
<style>
  #libraryTitle {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 0px;

    position: absolute;
    width: 540px;
    height: 98px;
    left: 50px;
    top: 59px;
  }
  </style>

  <style>
    #lightblueBox {
      position: absolute;
      width: 519px;
      height: 414px;
      left: 151px;
      top: 294px;

      background: rgba(255, 255, 255, 0.6);
      border: 1px solid #000000;
      box-sizing: border-box;
    }
  </style>

  <style>
    #whiteseperator {
      position: absolute;
      width: 674px;
      height: 866px;
      left: 766px;
      top: 0px;
    }
  </style>

  <style>
    #logoBook {
      position: absolute;
      width: 115.43px;
      height: 114.04px;
      left: calc(50% - 115.43px/2 - 641.26px);
      top: calc(50% - 114.04px/2 - 353.48px);

      transform: rotate(0.48deg);
    }
  </style>
  >--