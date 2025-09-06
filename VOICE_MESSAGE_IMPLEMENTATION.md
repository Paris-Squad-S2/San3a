# Voice Message Implementation Guide

## Overview
This implementation enables sending voice messages in the San3a messaging app. The voice message functionality follows the same pattern as image messages but handles audio files.

## Architecture Flow

### 1. Domain Layer
- `MessageContent.Audio(url: String, duration: Int, waves: List<Float>)` represents voice messages
- Contains the audio URL, duration in seconds, and waveform data for visualization

### 2. Data Layer
- `AudioDto(path: String, uri: Uri)` handles audio file upload metadata
- `StorageRemoteDataSource.saveVoiceFiles()` uploads audio files to Firebase Storage
- `MessagingRepositoryImpl.processVoiceMessage()` handles the upload and message creation
- `ChatMapper` converts between domain and DTO with voice fields

### 3. Presentation Layer
- `MessagesDetailsViewModel.sendVoiceMessage()` initiates voice message sending
- `MessageDetailsUiState` includes voice message fields and sending state
- `AudioPlayer` component displays voice messages (already existed)

## Usage Example

```kotlin
// In your UI component (e.g., voice recording button callback)
viewModel.sendVoiceMessage(
    voiceUri = recordedAudioUri,
    duration = recordingDurationInSeconds,
    waveform = extractedWaveformData
)
```

## Implementation Details

### Voice File Upload
1. Audio file URI is converted to `AudioDto` with Firebase Storage path
2. File is uploaded to `user{receiverId}/chat{chatId}/voice_{timestamp}.m4a`
3. Download URL is returned for use in the message

### Message Flow
1. `MessageContent.Audio` is created with local URI, duration, and waveform
2. Repository uploads audio file and gets download URL
3. `MessageDto` is created with `voiceUrl`, `voiceDuration`, and `voiceWaveform`
4. Message is sent to Firestore with voice metadata

### UI State Management
- `sendingVoiceMessage` state shows loading during upload
- Error handling reverts state and shows snackbar
- Success clears sending state

## Integration Points

### Required for Full Implementation
1. **Voice Recording Component**: UI for recording audio with duration and waveform extraction
2. **Audio Permission Handling**: Microphone permissions
3. **Audio File Format**: Ensure consistent audio format (suggested: .m4a)
4. **Waveform Generation**: Extract audio waveform for visualization

### Existing Components Used
- `AudioPlayer`: Already handles voice message playback and waveform display
- `MessageContent`: Shows voice messages when `onPlayClick` is provided
- Chat list shows 🎵 emoji for voice messages

## Testing
- Unit tests verify mapper logic for voice message conversion
- UI state tests ensure proper voice message field handling
- Integration with existing message infrastructure

## File Changes Made
- `AudioDto.kt` - New audio file upload DTO
- `StorageRemoteDataSource.kt` - Added `saveVoiceFiles()` method
- `FirebaseStorageDataSource.kt` - Implemented voice file upload
- `MessagingRepositoryImpl.kt` - Added voice message processing
- `ChatMapper.kt` - Updated mappers for voice message conversion
- `MessageDetailsUiState.kt` - Added voice message UI fields
- `MessagesDetailsViewModel.kt` - Added `sendVoiceMessage()` method