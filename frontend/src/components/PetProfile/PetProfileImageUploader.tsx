import { Dispatch, useEffect } from 'react';
import { styled } from 'styled-components';

import CameraIcon from '@/assets/svg/camera_icon.svg';
import DefaultDogIcon from '@/assets/svg/dog_icon.svg';
import { usePetAdditionContext } from '@/context/petProfile/PetAdditionContext';
import { useImageUpload } from '@/hooks/@common/useImageUpload';

import LoadingSpinner from '../@common/LoadingSpinner/LoadingSpinner';

interface PetProfileImageUploaderProps {
  updateIsValid?: Dispatch<React.SetStateAction<boolean>>;
}

const PetProfileImageUploader = (props: PetProfileImageUploaderProps) => {
  const { updateIsValid } = props;
  const { petProfile, updatePetProfile } = usePetAdditionContext();
  const {
    imageUrl,
    previewImage,
    compressionPercentage,
    isImageBeingUploaded,
    isImageBeingCompressed,
    uploadCompressedImage,
  } = useImageUpload();

  useEffect(() => {
    if (imageUrl) updatePetProfile({ imageUrl });
  }, [imageUrl]);

  useEffect(() => {
    if (updateIsValid) updateIsValid(!isImageBeingUploaded && !isImageBeingCompressed);
  }, [isImageBeingCompressed, isImageBeingUploaded, updateIsValid]);

  return (
    <ImageUploadLabel aria-label="사진 업로드하기">
      <input type="file" accept="image/*" onChange={uploadCompressedImage} />
      <PreviewImageWrapper>
        {isImageBeingCompressed && (
          <ProgressTracker>
            <p>이미지 압축 중({compressionPercentage}%)</p>
          </ProgressTracker>
        )}
        <PreviewImage src={previewImage || petProfile.imageUrl || DefaultDogIcon} alt="" />
      </PreviewImageWrapper>
      <CameraIconWrapper>
        <img src={CameraIcon} alt="" />
      </CameraIconWrapper>
      {isImageBeingUploaded && <LoadingSpinner />}
    </ImageUploadLabel>
  );
};

export default PetProfileImageUploader;

const PreviewImageWrapper = styled.div`
  position: relative;

  overflow: hidden;

  width: 16rem;
  height: 16rem;

  border: none;
  border: 1px solid ${({ theme }) => theme.color.grey300};
  border-radius: 50%;
`;

const ProgressTracker = styled.div`
  position: absolute;
  z-index: 100;
  top: 0;
  left: 0;

  display: flex;
  align-items: center;
  justify-content: center;

  width: inherit;
  height: inherit;

  opacity: 0.7;
  background-color: ${({ theme }) => theme.color.grey200};

  & > p {
    font-size: 1.6rem;

    opacity: 1;
  }
`;

const PreviewImage = styled.img`
  position: absolute;
  top: 0;
  left: 0;

  width: 100%;
  height: 100%;

  object-fit: cover;
  background-color: ${({ theme }) => theme.color.grey200};
`;

const ImageUploadLabel = styled.label`
  cursor: pointer;

  position: relative;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 16rem;
  height: 16rem;

  background-image: url('https://velog.velcdn.com/images/chex/post/9505d4fb-5850-4ce8-9575-04cece41a185/image.png');
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
  border-radius: 50%;

  & > input {
    display: none;
  }
`;

const CameraIconWrapper = styled.div`
  position: absolute;
  z-index: 200;
  right: 0;
  bottom: 0;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 5rem;
  height: 5rem;

  background-color: ${({ theme }) => theme.color.white};
  border: 1px solid ${({ theme }) => theme.color.grey300};
  border-radius: 50%;
`;
