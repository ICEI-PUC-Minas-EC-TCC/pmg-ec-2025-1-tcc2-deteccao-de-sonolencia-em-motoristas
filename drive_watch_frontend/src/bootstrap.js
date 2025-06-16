/**
 * Esse modulo inicializa o axios e o configura
 * Carrega a biblioteca Axios para requisicoes http
 */
import axios from 'axios';
window.axios = axios;

window.axios.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
