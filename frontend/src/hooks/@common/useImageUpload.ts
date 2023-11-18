import imageCompression from 'browser-image-compression';
import { ChangeEvent, useState } from 'react';

import { PET_PROFILE_IMAGE_COMPRESSION_OPTION } from '@/constants/petProfile';
import { useToast } from '@/context/Toast/ToastContext';
import { useUploadImageMutation } from '@/hooks/query/image';

const MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB;

export const useImageUpload = () => {
  const { toast } = useToast();
  const { uploadImageMutation } = useUploadImageMutation();
  const [previewImage, setPreviewImage] = useState('');
  const [imageUrl, setImageUrl] = useState('');
  const [compressionPercentage, setCompressionPercentage] = useState(-1);
  const isImageBeingCompressed = compressionPercentage > -1 && compressionPercentage < 100;

  const uploadCompressedImage = async (e: ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files) return;

    const originalImageFile = e.target.files[0];

    if (!originalImageFile) return;
    if (originalImageFile.size > MAX_FILE_SIZE) {
      e.target.value = '';
      alert('이미지 크기가 너무 큽니다. 5MB 이하의 이미지를 업로드해주세요.');

      return;
    }

    setCompressionPercentage(0);
    setPreviewImage(URL.createObjectURL(originalImageFile));

    const imageUploadFormData = new FormData();
    const compressedImageBlob = await imageCompression(originalImageFile, {
      ...PET_PROFILE_IMAGE_COMPRESSION_OPTION,
      onProgress: progress => setCompressionPercentage(progress),
    });

    setCompressionPercentage(-1);

    imageUploadFormData.append('image', compressedImageBlob);

    uploadImageMutation.uploadImage({ imageFile: imageUploadFormData }).then(data => {
      setImageUrl(data.imageUrl);
      toast.success('이미지 업로드가 완료됐어요!');
    });
  };

  const deletePreviewImage = () => {
    URL.revokeObjectURL(previewImage);
    setPreviewImage('');
  };

  return {
    imageUrl,
    previewImage,
    compressionPercentage,
    isImageBeingUploaded: uploadImageMutation.isLoading,
    isImageBeingCompressed,
    uploadCompressedImage,
    deletePreviewImage,
  };
};
