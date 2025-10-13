import { fetchJSON, showError, showLoading, getIdFromPath, showFieldErrors } from '../utils.js';
import { setPageMeta } from '../meta.js';

document.addEventListener('DOMContentLoaded', async () => {
  const form = document.getElementById('studentForm');
  const id = getIdFromPath();
  const metaUrl = id ? '/api/meta/student-edit' : '/api/meta/student-new';

  try {
    const meta = await fetchJSON(metaUrl);
    setPageMeta(meta);
  } catch (err) {
    console.warn('Meta load failed:', err);
  }

  if (id) await loadStudent(id);

  form?.addEventListener('submit', async (e) => handleSubmit(e, id));
});

async function loadStudent(id) {
  const form = document.getElementById('studentForm');
  const nameInput = document.getElementById('name');
  const courseInput = document.getElementById('course');
  showLoading(form, 'Loading student...');

  try {
    const student = await fetchJSON(`/api/students/${id}`);
    nameInput.value = student.name || '';
    courseInput.value = student.course || '';
  } catch (err) {
    showError(err.message || 'Failed to load student.', form);
  }
}

async function handleSubmit(e, id) {
  e.preventDefault();
  const form = e.target;

  const payload = {
    name: form.name.value.trim(),
    course: form.course.value.trim(),
  };

  const method = id ? 'PUT' : 'POST';
  const url = id ? `/api/students/${id}` : '/api/students';
  showLoading(form, id ? 'Updating...' : 'Creating...');

  try {
    await fetchJSON(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    });

    form.innerHTML = `
      <div class="alert alert-success text-center">
        Student ${id ? 'updated' : 'added'} successfully!
      </div>
      <a href="/students" class="btn btn-outline-secondary w-100 mt-3">Back to List</a>
    `;
  } catch (err) {
    console.error(err);

    // If backend returned structured validation errors
    if (err.details && typeof err.details === 'object') {
      showFieldErrors(err.details, form);
    } else {
      showError(err.message || `Failed to ${id ? 'update' : 'add'} student.`, form);
    }
  }
}
