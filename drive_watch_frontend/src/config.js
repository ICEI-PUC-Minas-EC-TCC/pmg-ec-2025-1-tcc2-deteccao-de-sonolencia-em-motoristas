// DEFINES CONSTANTS AND USEFUL GLOBALS

//export const API_BASE = "http://localhost:8080/api/v1/"
//export const API_BASE = "https://6609d2960f324a9a2883d1bb.mockapi.io/api/v1/"
export const API_BASE = "https://drivewatchbackend-production.up.railway.app/api/v1/";


export const FRONTEND_BASE = "http://localhost:5173/";

// ENDPOINTS GET
const register = "register";
//const eventos = "event";
const eventos = "register";
const devices = "device";
const company = "company";
const address = "address";
const phone = "phone/";

//const urli = "api/i/";

// ENDPOINTS POST
//const salvar = "salvar";
//const upload = "upload";

// ENDPOINTS UPDATE
export const pessoas_up = "pessoas/up";

export const URL_REGISTER = API_BASE + register;
export const URL_EVENTOS = API_BASE + eventos;
export const URL_DEVICE = API_BASE + devices;
export const URL_PESSOAS_UP = API_BASE + pessoas_up;
export const URL_ACESSO = API_BASE + "tem_acesso";
export const URL_REGISTER_BY_DEVICE = API_BASE + register + '/' + devices + '/';
export const URL_PHONE = API_BASE + phone;
export const URL_PHONE_BY_DEVICE = API_BASE + phone + devices + '/';
export const URL_ADDRESS = API_BASE + address;
export const URL_ADDRESS_BY_COMPANY = API_BASE + address + '/' + company + '/';
export const URL_COMPANY = API_BASE + company;
export const URL_COMPANY_GETALL = URL_COMPANY + '/getAll';
//export const URL_I = API_BASE + urli;
//export const URL_SALVAR = API_BASE + salvar;
//export const URL_UPLOAD = API_BASE + upload;