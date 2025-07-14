export const uploadMultipleFiles = async (files, baseTitle, description, onProgress) => {
    const formData = new FormData();

    files.forEach((file, index) => {
        formData.append('files', file);
    });

    formData.append('title', baseTitle);
    formData.append('description', description);

    const response = await fetch('/api/documents/upload/multiple', {
        method: 'POST',
        body: formData,
    });

    if (!response.ok) {
        throw new Error('Upload failed');
    }

    return response.json();
};
