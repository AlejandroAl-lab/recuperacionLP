let currentTab = 'users';
let headers = new Headers();

function actualizarCredenciales() {
    const authVal = document.getElementById('auth-user').value;
    headers.set('Authorization', 'Basic ' + btoa(authVal));
}
actualizarCredenciales();

function switchTab(tab) {
    currentTab = tab;
    document.querySelectorAll('.tab-btn').forEach(btn => {
        btn.classList.toggle('active', btn.textContent.toLowerCase().includes(tab === 'promos' ? 'promoc' : tab === 'users' ? 'usua' : 'redenc'));
    });
    document.querySelectorAll('.form-fields').forEach(el => el.classList.add('hidden'));
    document.getElementById(`fields-${tab}`).classList.remove('hidden');
    limpiarFormulario();
}

async function realizarPeticion(url, options = {}) {
    options.headers = headers;
    if (options.body) {
        headers.set('Content-Type', 'application/json');
    }
    try {
        const response = await fetch(url, options);
        if (response.status === 401 || response.status === 403) {
            alert("⚠️ Error de Seguridad: No tienes permisos suficientes para realizar esta acción.");
            return null;
        }
        if (!response.ok) throw new Error("Error en la solicitud");
        if (response.status === 24) return true;
        return await response.json();
    } catch (err) {
        console.error(err);
        alert("Error al procesar el requerimiento.");
        return null;
    }
}

async function cargarListas() {
    const usuarios = await realizarPeticion('/api/users');
    if (usuarios) {
        const tbody = document.querySelector('#table-users tbody');
        tbody.innerHTML = '';
        const userSelect = document.getElementById('redemption-user-id');
        userSelect.innerHTML = '';
        usuarios.forEach(u => {
            tbody.innerHTML += `<tr><td>${u.id}</td><td>${u.email}</td><td>${u.name}</td><td>${u.gender ? 'M' : 'F'}</td><td>${u.birthday || ''}</td>
            <td><button class="btn btn-edit" onclick="editarRegistro('users', ${u.id})">Editar</button><button class="btn btn-delete" onclick="eliminarRegistro('users', ${u.id})">Borrar</button></td></tr>`;
            userSelect.innerHTML += `<option value="${u.id}">${u.name}</option>`;
        });
    }

    const promos = await realizarPeticion('/api/promos');
    if (promos) {
        const tbody = document.querySelector('#table-promos tbody');
        tbody.innerHTML = '';
        const promoSelect = document.getElementById('redemption-promo-id');
        promoSelect.innerHTML = '';
        promos.forEach(p => {
            tbody.innerHTML += `<tr><td>${p.id}</td><td>${p.title}</td><td>${p.peopleLimit}</td><td>${p.expirationDate}</td>
            <td><button class="btn btn-edit" onclick="editarRegistro('promos', ${p.id})">Editar</button><button class="btn btn-delete" onclick="eliminarRegistro('promos', ${p.id})">Borrar</button></td></tr>`;
            promoSelect.innerHTML += `<option value="${p.id}">${p.title}</option>`;
        });
    }

    const redenciones = await realizarPeticion('/api/redemptions');
    if (redenciones) {
        const tbody = document.querySelector('#table-redemptions tbody');
        tbody.innerHTML = '';
        redenciones.forEach(r => {
            tbody.innerHTML += `<tr><td>${r.id}</td><td>${r.code}</td><td>${r.user?.name || ''}</td><td>${r.promo?.title || ''}</td><td>${r.redeemed ? 'Sí' : 'No'}</td>
            <td><button class="btn btn-edit" onclick="editarRegistro('redemptions', ${r.id})">Editar</button><button class="btn btn-delete" onclick="eliminarRegistro('redemptions', ${r.id})">Borrar</button></td></tr>`;
        });
    }
}

async function procesarFormulario(e) {
    e.preventDefault();
    const id = document.getElementById('entity-id').value;
    let payload = {};
    let url = `/api/${currentTab}`;
    let method = id ? 'PUT' : 'POST';
    if (id) url += `/${id}`;

    if (currentTab === 'users') {
        payload = {
            email: document.getElementById('user-email').value,
            name: document.getElementById('user-name').value,
            gender: document.getElementById('user-gender').value === 'true',
            birthday: document.getElementById('user-birthday').value || null,
            password: document.getElementById('user-password').value || '123456'
        };
    } else if (currentTab === 'promos') {
        payload = {
            title: document.getElementById('promo-title').value,
            description: document.getElementById('promo-description').value,
            terms: document.getElementById('promo-terms').value,
            expirationDate: document.getElementById('promo-expiration').value,
            peopleLimit: parseInt(document.getElementById('promo-limit').value)
        };
    } else if (currentTab === 'redemptions') {
        payload = {
            code: document.getElementById('redemption-code').value,
            user: { id: parseInt(document.getElementById('redemption-user-id').value) },
            promo: { id: parseInt(document.getElementById('redemption-promo-id').value) },
            redeemed: document.getElementById('redemption-status').value === 'true'
        };
    }

    const res = await realizarPeticion(url, { method: method, body: JSON.stringify(payload) });
    if (res) { limpiarFormulario(); cargarListas(); }
}

async function editarRegistro(tab, id) {
    switchTab(tab);
    const data = await realizarPeticion(`/api/${tab}/${id}`);
    if (!data) return;

    document.getElementById('entity-id').value = data.id;
    document.getElementById('form-title').textContent = `Editar Registro #${data.id}`;

    if (tab === 'users') {
        document.getElementById('user-email').value = data.email;
        document.getElementById('user-name').value = data.name;
        document.getElementById('user-gender').value = data.gender.toString();
        document.getElementById('user-birthday').value = data.birthday || '';
    } else if (tab === 'promos') {
        document.getElementById('promo-title').value = data.title;
        document.getElementById('promo-description').value = data.description || '';
        document.getElementById('promo-terms').value = data.terms || '';
        document.getElementById('promo-expiration').value = data.expirationDate;
        document.getElementById('promo-limit').value = data.peopleLimit;
    } else if (tab === 'redemptions') {
        document.getElementById('redemption-code').value = data.code;
        document.getElementById('redemption-user-id').value = data.user?.id;
        document.getElementById('redemption-promo-id').value = data.promo?.id;
        document.getElementById('redemption-status').value = data.redeemed.toString();
    }
}

async function eliminarRegistro(tab, id) {
    if (!confirm("¿Eliminar este registro?")) return;
    const res = await realizarPeticion(`/api/${tab}/${id}`, { method: 'DELETE' });
    if (res !== null) cargarListas();
}

function limpiarFormulario() {
    document.getElementById('crud-form').reset();
    document.getElementById('entity-id').value = '';
    document.getElementById('form-title').textContent = "Crear / Editar Registro";
}

document.addEventListener('DOMContentLoaded', cargarListas);