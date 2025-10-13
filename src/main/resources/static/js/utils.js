/**
 * Generic utility helpers — used across student pages and admin.
 * Pure JavaScript, no page-specific logic.
 */

// /js/utils.js
export async function fetchJSON(url, options = {}) {
  const res = await fetch(url, options);
  const contentType = res.headers.get('Content-Type') || '';

  let data = null;
  if (contentType.includes('application/json')) {
    data = await res.json();
  } else {
    data = await res.text();
  }

  if (!res.ok) {
    // Handle Spring validation error object
    if (data && typeof data === 'object' && (data.fieldErrors || data.globalErrors)) {
      throw data; // Pass structured validation errors up
    }

    // Otherwise throw normal message
    throw new Error(data?.message || data || 'Request failed');
  }

  return data;
}


/**
 * Show a Bootstrap-style error message inside a container.
 */
export function showError(msg, target) {
  const element = typeof target === 'string' ? document.getElementById(target) : target;
  if (!element) return alert(msg);
  element.innerHTML = `
    <div class="alert alert-danger text-center py-3 my-2" role="alert">
      ${msg}
    </div>`;
}

/**
 * Displays field-specific validation errors under each input.
 * Expects an object like: { name: "Name is required", course: "Course cannot be blank" }
 */
export function showFieldErrors(errors, form) {
  if (!errors || typeof errors !== 'object') return;

  // Clear any old messages
  form.querySelectorAll('.invalid-feedback').forEach(el => el.remove());
  form.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));

  Object.entries(errors).forEach(([field, message]) => {
    const input = form.querySelector(`[name="${field}"]`);
    if (input) {
      input.classList.add('is-invalid');
      const feedback = document.createElement('div');
      feedback.className = 'invalid-feedback';
      feedback.textContent = message;
      input.insertAdjacentElement('afterend', feedback);
    }
  });
}


/**
 * Show a Bootstrap-style success message.
 */
export function showSuccess(msg, target) {
  const element = typeof target === 'string' ? document.getElementById(target) : target;
  if (!element) return;
  element.innerHTML = `
    <div class="alert alert-success text-center py-3 my-2" role="alert">
      ${msg}
    </div>`;
}

/**
 * Extract numeric ID from URL path (e.g. /students/5 → 5).
 */
export function getIdFromPath() {
  const parts = window.location.pathname.split('/');
  const id = parts.find(p => /^\d+$/.test(p));
  return id ? parseInt(id, 10) : null;
}

/**
 * Display a simple loading indicator.
 */
export function showLoading(target, text = 'Loading...') {
  const element = typeof target === 'string' ? document.getElementById(target) : target;
  if (!element) return;
  element.innerHTML = `
    <div class="text-center text-muted py-4">
      <div class="spinner-border spinner-border-sm me-2" role="status"></div>${text}
    </div>`;
}
