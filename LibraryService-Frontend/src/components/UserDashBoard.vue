
<template>
  <div id="userAccount">
    <h2>Your Account Information</h2>
        <div> 
            <b-container id="profileInfo"> 
          
                <b-row align-h="center">
                    <b-avatar class= "my-2" ></b-avatar>
                    
                   
                </b-row>
              
                 <b-row align-h="center">    
                   <b-badge href="#" variant="success">Activated</b-badge>
                 </b-row>
               
                <b-row class="my-4">
                    <b-col >
                        <h3>Personal Information</h3>
                        <b-row align-h="center">
                            <div class="my-2">
                            <b>First Name:</b>
                            <b-form-input v-model="text" placeholder="Jonh"></b-form-input>
                            </div>

                            <div class="my-2">
                            <b>Last Name:</b>
                            <b-form-input v-model="text" placeholder="Doe"></b-form-input>
                            </div>
                        </b-row>
                    
                        <b-row align-h="center">
                            <div class="my-2">
                                <b>Email:</b>
                                <b-form-input v-model="text" placeholder="sarah@mail.ca"></b-form-input>
                            </div>


                            <div class="my-2">
                                <b>Address:</b>
                                <b-form-input v-model="text" placeholder="123 charming avenue"></b-form-input>
                            </div>
                        </b-row> 
                        <b-row align-h="center" class="my-2">
                            
                                <b>Change Passowrd:</b>
                                <b-form-input v-model="text" placeholder="New Passowrd"></b-form-input>
                               <b-form-input v-model="text" placeholder="Comfirm Password"></b-form-input>
                        </b-row>
                        <b-row align-h="center" class="my-5" >
                               <b-button block variant="primary">Comfirm changes </b-button>
                             
                        </b-row>   
                        
                    </b-col>
                   
                    <b-col>

                        <div class="mybalance">
                            <h3>Current Balance</h3>

                        </div>
                    
                    </b-col>

                    <b-col>

                        <div class="myTranscations">
                            <h3>Transaction History</h3>
                        </div>
                    
                    </b-col>

                 </b-row>
            </b-container>
        </div>
        
        
        
    </div>

 





</template>

<script>
</script>


<style>



</style>

