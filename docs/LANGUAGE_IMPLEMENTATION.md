# Language Change Implementation

This document describes how the language change functionality works in the San3a app to ensure immediate reflection across all screens.

## Problem Statement

Previously, when users changed the language through the language selection bottom sheet, the change would be saved to persistent storage (DataStore) but would not immediately reflect across all screens of the app. Users had to restart the app or navigate away and back to see the language change.

## Solution

The solution implements a **dual-state language management system**:

1. **Immediate State Management**: Uses Compose's `CompositionLocal` for immediate UI updates
2. **Persistent State Management**: Uses DataStore for persistence across app sessions

## Implementation Details

### 1. Language State Management (`LanguageState.kt`)

```kotlin
val LocalAppLanguage = staticCompositionLocalOf<MutableState<String>> { 
    mutableStateOf("en") 
}
```

- Creates a composition local that holds the current language state
- Provides hooks for accessing current language in any composable

### 2. San3aScaffold Updates

The main scaffold now:
- Creates a local language state for immediate updates
- Syncs with persisted language on app start
- Provides the language state through `CompositionLocalProvider`
- Updates `LocalConfiguration` and `LocalLayoutDirection` immediately when language changes

### 3. MoreScreen Updates

The language selection flow now:
- Updates the composition local immediately when user selects a language
- Also persists the change to DataStore for future app launches
- This ensures immediate UI reflection across all screens

## Usage

### Getting Current Language

```kotlin
@Composable
fun MyComponent() {
    val currentLanguage = currentAppLanguage()
    // Use currentLanguage...
}
```

### Getting Language State

```kotlin
@Composable
fun MyComponent() {
    val languageState = rememberAppLanguage()
    // Can read: languageState.value
    // Can write: languageState.value = "ar"
}
```

## Flow Diagram

```
User Selects Language
        ↓
1. Update CompositionLocal (Immediate UI Update)
        ↓
2. Persist to DataStore (For Next App Launch)
        ↓
3. All Screens Recompose with New Language
```

## Benefits

1. **Immediate Feedback**: Language changes are reflected immediately without app restart
2. **Persistent**: Language preference is saved for future app launches
3. **Global**: All screens automatically receive the language change
4. **Efficient**: Uses Compose's recomposition system efficiently

## Testing

The implementation includes unit tests that verify:
- Language state changes trigger recomposition
- All components receive the updated language immediately
- Composition local provides correct language values