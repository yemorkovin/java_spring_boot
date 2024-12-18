const apiBaseUrl = 'http://localhost:8080/categories';

// Elements
const categoryForm = document.getElementById('category-form');
const categoryList = document.getElementById('category-list');
const categoryIdInput = document.getElementById('category-id');
const nameInput = document.getElementById('name');
const descriptionInput = document.getElementById('description');

// Fetch and display all categories
async function fetchCategories() {
    const response = await fetch(apiBaseUrl);
    const categories = await response.json();

    categoryList.innerHTML = categories.map(category => `
        <li>
            <span><strong>${category.name}</strong>: ${category.description}</span>
            <div>
                <button class="edit" onclick="editCategory(${category.id}, '${category.name}', '${category.description}')">Edit</button>
                <button class="delete" onclick="deleteCategory(${category.id})">Delete</button>
            </div>
        </li>
    `).join('');
}

categoryForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const id = categoryIdInput.value;
    const name = nameInput.value;
    const description = descriptionInput.value;

    const category = { name, description };

    const method = id ? 'PUT' : 'POST';
    const url = id ? `${apiBaseUrl}/${id}` : apiBaseUrl;
    console.log(url);
    await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(category),
    });

    // Reset form and refresh categories
    categoryIdInput.value = '';
    categoryForm.reset();
    fetchCategories();
});

// Populate form for editing
function editCategory(id, name, description) {
    categoryIdInput.value = id;
    nameInput.value = name;
    descriptionInput.value = description;
}

// Delete a category
async function deleteCategory(id) {
    await fetch(`${apiBaseUrl}/${id}`, { method: 'DELETE' });
    fetchCategories();
}

// Initial fetch
fetchCategories();
