import './bootstrap'; //Inicializa axios e o configura
import { createApp } from 'vue';

// Importa a estilizacao
import './style/app.scss';

// Biblioteca CSS Bootstrap
//import * as bootstrap from 'bootstrap';

import router from './router';
import App from './Pages/App.vue';


// =================== FIM IMPORTS ==========================

// Cria APP Vue
const app = createApp(App);

// Roteamento SPA por vue-router instanciado aqui. 
app.use(router);

// INICIALIZA O VUE OFICIALMENTE
app.mount('#app');

//Manipule a animacao de load. Quando o vue eh montado ele esconde o carregando.
const loadingOverlay = document.querySelector('#loadingOverlay');
if (loadingOverlay) {
  loadingOverlay.style.display = 'none';
}