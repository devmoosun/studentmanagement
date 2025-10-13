// /js/meta.js

/**
 * Sets the page meta tags dynamically.
 * @param {Object} meta - { title, description, keywords }
 */
export function setPageMeta(meta) {
    if (!meta) return;

    const titleSuffix = " | devmoosun";
    document.title = meta.title + titleSuffix;

    const descTag = document.querySelector('meta[name="description"]');
    const keyTag = document.querySelector('meta[name="keywords"]');

    if (descTag) descTag.content = meta.description || '';
    if (keyTag) keyTag.content = meta.keywords || '';
}

/**
 * Automatically applies meta if `window.pageMeta` exists.
 * Call this on every page.
 */
export function autoApplyMeta() {
    if (window.pageMeta) {
        setPageMeta(window.pageMeta);
    }
}

// Immediately apply if this module is imported
autoApplyMeta();
