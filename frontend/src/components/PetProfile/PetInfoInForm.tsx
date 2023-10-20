import { useEffect } from 'react';
import { styled } from 'styled-components';

import CameraIcon from '@/assets/svg/camera_icon.svg';
import { useImageUpload } from '@/hooks/@common/useImageUpload';
import { PetProfile } from '@/types/petProfile/client';

import { getGenderImage, getPetAge } from './PetItem';

interface PetInfoInFormProps {
  petItem: PetProfile;
  onChangeImage: (imageUrl: string) => void;
}

const PetInfoInForm = (petInfoInFormProps: PetInfoInFormProps) => {
  const { petItem, onChangeImage } = petInfoInFormProps;
  const { previewImage, imageUrl, uploadImage } = useImageUpload();

  useEffect(() => {
    if (imageUrl) onChangeImage(imageUrl);
  }, [imageUrl]);

  return (
    <PetInfoContainer>
      <PetImageAndDetail>
        <ImageUploadLabel>
          <input type="file" accept="image/*" onChange={uploadImage} />
          <PetImageWrapper>
            <PetImage src={previewImage || petItem.imageUrl} alt={petItem.name} />
          </PetImageWrapper>
          <CameraIconWrapper>
            <CameraImage src={CameraIcon} alt="" />
          </CameraIconWrapper>
        </ImageUploadLabel>
        <div>
          <GenderAndName>
            <GenderImageWrapper>
              <GenderImage src={getGenderImage(petItem.gender)} />
            </GenderImageWrapper>
            <PetName>{petItem.name}</PetName>
          </GenderAndName>
          <InfoDetail>{`${petItem.breed} / ${getPetAge(petItem.age)}`}</InfoDetail>
          <InfoDetail>{`${petItem.petSize} / ${petItem.weight}kg`}</InfoDetail>
        </div>
      </PetImageAndDetail>
    </PetInfoContainer>
  );
};

export default PetInfoInForm;

const PetInfoContainer = styled.div`
  display: flex;
`;

const PetImageAndDetail = styled.div`
  display: flex;
  gap: 1.6rem;
  align-items: center;
`;

const ImageUploadLabel = styled.label`
  cursor: pointer;

  position: relative;

  width: 10rem;
  height: 10rem;

  background-color: ${({ theme }) => theme.color.grey200};
  border: 1px solid ${({ theme }) => theme.color.grey300};
  border-radius: 50%;

  & > input {
    display: none;
  }
`;

const CameraIconWrapper = styled.div`
  position: absolute;
  right: 0;
  bottom: 0;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 3rem;
  height: 3rem;

  background-color: ${({ theme }) => theme.color.white};
  border: 1px solid ${({ theme }) => theme.color.grey300};
  border-radius: 50%;
`;

const CameraImage = styled.img`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  width: 70%;
  height: 70%;

  object-fit: cover;
`;

const PetName = styled.p`
  margin-top: 0.4rem;
  margin-left: 0.4rem;

  font-size: 2.2rem;
  font-weight: 700;
  line-height: 2.4rem;
  color: ${({ theme }) => theme.color.grey700};
  letter-spacing: -0.5px;
`;

const GenderAndName = styled.div`
  display: flex;
  align-items: center;

  margin-bottom: 0.8rem;
`;

const InfoDetail = styled.p`
  margin-bottom: 0.4rem;

  font-size: 1.6rem;
  font-weight: 500;
  line-height: 1.6rem;
  color: ${({ theme }) => theme.color.grey400};
  letter-spacing: -0.5px;
`;

const PetImageWrapper = styled.div`
  position: relative;

  overflow: hidden;

  width: 10rem;
  height: 10rem;

  background-color: ${({ theme }) => theme.color.white};
  border-radius: 50%;
`;

const PetImage = styled.img`
  position: absolute;
  top: 0;
  left: 0;

  width: 100%;
  height: 100%;

  object-fit: cover;
`;

const GenderImageWrapper = styled.div`
  position: relative;

  overflow: hidden;

  width: 2rem;
  height: 2rem;

  background-color: ${({ theme }) => theme.color.grey200};
  border-radius: 50%;
`;

const GenderImage = styled.img`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  width: 70%;
  height: 70%;

  object-fit: cover;
`;
