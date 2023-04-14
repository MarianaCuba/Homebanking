const { createApp } = Vue
console.log("hola");
createApp({
    data(){
    return{
        datos:[],
        firstName: "" ,
        lastName:"",
        creationDate:"",
        loans:[]
        
    }
},

created(){
    this.loadData()

},
  methods: {
    loadData(){
        axios.get('http://localhost:8080/api/clients/1')
        .then(response => {
          // console.log(response)
            this.datos = response.data;
            this.loans = this.datos.loans
            console.log(this.datos);
          
        } )
         .catch(error => console.log(error));
    },


     }


}).mount("#app")