import React, { useState } from 'react';
import {
    Box,
    Button,
    FormControl,
    FormLabel,
    Input,
    Text,
    VStack,
    useToast,
    Progress,
    Textarea,
} from '@chakra-ui/react';
import { AttachmentIcon } from '@chakra-ui/icons';
import DragDropUpload from './DragDropUpload';
import Card from './card/Card';


const MultipleFileUpload = () => {
    const [files, setFiles] = useState([]);
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [isUploading, setIsUploading] = useState(false);
    const [progress, setProgress] = useState(0);
    const toast = useToast();

    const handleFileChange = (event) => {
        const selectedFiles = Array.from(event.target.files);
        setFiles(selectedFiles);
    };

    const handleUpload = async (e) => {
        e.preventDefault();

        if (!files.length) {
            toast({
                title: 'No files selected',
                status: 'warning',
                duration: 3000,
                isClosable: true,
            });
            return;
        }

        if (!title.trim()) {
            toast({
                title: 'Title is required',
                status: 'warning',
                duration: 3000,
                isClosable: true,
            });
            return;
        }

        setIsUploading(true);
        const formData = new FormData();

        files.forEach(file => {
            formData.append('files', file);
        });

        formData.append('title', title);
        formData.append('description', description);

        try {
            const response = await fetch('/api/upload/multiple', {
                method: 'POST',
                body: formData,
            });

            if (!response.ok) {
                throw new Error('Upload failed');
            }

            const result = await response.json();

            toast({
                title: 'Upload successful',
                description: `Successfully uploaded ${files.length} files`,
                status: 'success',
                duration: 5000,
                isClosable: true,
            });

            // Reset form
            setFiles([]);
            setTitle('');
            setDescription('');
            document.getElementById('file-upload').value = '';

        } catch (error) {
            toast({
                title: 'Upload failed',
                description: error.message,
                status: 'error',
                duration: 5000,
                isClosable: true,
            });
        } finally {
            setIsUploading(false);
            setProgress(0);
        }
    };

    return (
        <Card>
                <Text fontSize="xl" fontWeight="bold">Upload Multiple Files</Text>
                <form onSubmit={handleUpload}>
                    <VStack spacing={4} align="stretch">
                        <FormControl isRequired>
                            <FormLabel>Base Title</FormLabel>
                            <Input
                                value={title}
                                onChange={(e) => setTitle(e.target.value)}
                                placeholder="Enter base title for files"
                            />
                        </FormControl>

                        <FormControl>
                            <FormLabel>Description</FormLabel>
                            <Textarea
                                value={description}
                                onChange={(e) => setDescription(e.target.value)}
                                placeholder="Enter description for files"
                            />
                        </FormControl>

                        <FormControl isRequired>
                            <FormLabel>Files</FormLabel>
                            <DragDropUpload
                                onFileSelect={setFiles}
                                id="file-upload"
                                type="file"
                                multiple
                                onChange={handleFileChange}
                                accept="*/*"
                                padding={1}
                            />
                            <Text fontSize="sm" color="gray.500" mt={1}>
                                Selected files: {files.length}
                            </Text>
                        </FormControl>

                        {files.length > 0 && (
                            <Box>
                                <Text fontSize="sm" fontWeight="medium">Selected Files:</Text>
                                {files.map((file, index) => (
                                    <Text key={index} fontSize="sm" color="gray.600">
                                        {file.name} ({(file.size / 1024).toFixed(2)} KB)
                                    </Text>
                                ))}
                            </Box>
                        )}

                        {isUploading && (
                            <Progress value={progress} size="xs" colorScheme="blue" />
                        )}

                        <Button
                            type="submit"
                            colorScheme="blue"
                            isLoading={isUploading}
                            leftIcon={<AttachmentIcon />}
                        >
                            Upload Files
                        </Button>
                    </VStack>
                </form>
        </Card>
    );
};

export default MultipleFileUpload;
