// /js/students.js
import { fetchJSON, showError, showLoading, getIdFromPath } from '../utils.js';
import { setPageMeta } from '../meta.js';

document.addEventListener('DOMContentLoaded', async () => {
  await loadPage();
  document.getElementById('refreshBtn')?.addEventListener('click', loadPage);
});

async function loadPage() {
  const tbody = document.getElementById('students-body');
  showLoading(tbody);

  try {
    // Load SEO metadata
    const meta = await fetchJSON('/api/meta/students');
    setPageMeta(meta);

    // Load students
    const students = await fetchJSON('/api/students');
    renderStudents(students);
  } catch (err) {
    console.error(err);
    showError(err.message || 'Failed to load students.', tbody);
  }
}

function renderStudents(students) {
  const tbody = document.getElementById('students-body');
  if (!tbody) return;

  if (!students.length) {
    showError('No students found.', tbody);
    return;
  }

  tbody.innerHTML = students
    .map(
      (s) => `
      <tr>
        <td>${s.id}</td>
        <td>${s.name}</td>
        <td>${s.course}</td>
        <td class="text-end">
          <a href="/students/${s.id}/edit" class="btn btn-sm btn-outline-primary me-2">Edit</a>
          <button class="btn btn-sm btn-outline-danger" data-id="${s.id}">Delete</button>
        </td>
      </tr>`
    )
    .join('');

  // Attach delete handlers
  tbody.querySelectorAll('button[data-id]').forEach((btn) =>
    btn.addEventListener('click', async () => await deleteStudent(btn.dataset.id))
  );
}

async function deleteStudent(id) {
  if (!confirm('Are you sure you want to delete this student?')) return;
  const tbody = document.getElementById('students-body');
  showLoading(tbody, 'Deleting...');

  try {
    await fetchJSON(`/api/students/${id}`, { method: 'DELETE' });
    await loadPage();
  } catch (err) {
    console.error(err);
    showError(err.message || 'Failed to delete student.', tbody);
  }
}
